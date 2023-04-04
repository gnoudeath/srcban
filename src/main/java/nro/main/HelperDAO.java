package nro.main;

import java.sql.*;

import nro.constant.Constant;
import nro.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HelperDAO {
    public static String getTopPower() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_POWER = "SELECT name, power FROM player WHERE account_id > 3 ORDER BY power DESC LIMIT " + Constant.MAX_TOP_POWER;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = DataSource.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while(rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("power")).append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
     public static String getTopsukiennaubanh() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_POWER = "SELECT name, diemsukien FROM player WHERE diemsukien >1  ORDER BY diemsukien DESC LIMIT " + Constant.MAX_TOP_POWER;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = DataSource.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while(rs.next()) {
                
                sb.append("|3|Top").append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("diemsukien")).append("  Điểm Sự Kiện\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    public static String getTopsukien() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_POWER = "SELECT name, event_pointnew FROM player WHERE event_pointnew >1  ORDER BY event_pointnew DESC LIMIT " + Constant.MAX_TOP_POWER;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = DataSource.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while(rs.next()) {
                
                sb.append("Top").append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("event_pointnew")).append("  Điểm Sự Kiện\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getTopCard() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_CARD = "SELECT player.name, account.tongnap FROM player INNER JOIN account ON player.account_id = account.id WHERE account.tongnap > 0 AND account.id > 3 ORDER BY account.tongnap DESC LIMIT " + Constant.MAX_TOP_CARD;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = DataSource.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_CARD);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while(rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("tongnap")).append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getTopPoint() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_CARD = "SELECT name, total_point FROM player WHERE account_id > 3 ORDER BY total_point DESC LIMIT " + Constant.MAX_TOP_POINT;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = DataSource.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_CARD);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while(rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("total_point")).append(" điểm").append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    public static String getTopBXHLTN() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_CARD = "SELECT name, event_ltn FROM player WHERE event_ltn > 1 ORDER BY event_ltn DESC LIMIT " + Constant.MAX_TOP_POINT;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = DataSource.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_CARD);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while(rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("event_ltn")).append(" điểm").append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static boolean payMoneyDB(Player p, int money, boolean isActive) {
        String SELECT_BALANCE = "SELECT balance FROM account WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        boolean check = true;
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement(SELECT_BALANCE);
            ps.setInt(1, p.id);
            conn.setAutoCommit(false);
            rs = ps.executeQuery();
            if (rs.next()) {
                int balance = rs.getInt("balance");
                if(balance >= money) {
                    String UPDATE_ACCOUNT = "UPDATE account SET balance=? WHERE id=?";
                    if(isActive) {
                        UPDATE_ACCOUNT = "UPDATE account SET balance=?,active=1 WHERE id=?";
                    }
                    ps = conn.prepareStatement(UPDATE_ACCOUNT);
                    ps.setInt(1, balance - money);
                    ps.setInt(2, p.id);
                    ps.executeUpdate();
                    conn.commit();
                    ps.close();

                    if(isActive) {
                        p.isActive = true;
                    }
                    p.balance -= money;
                    check = true;
                } else {
                    check = false;
                }
            }
            conn.close();
        } catch (Exception e) {
            check = false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }
    public static void PointEventTORI(Player p, int sodiem) {
        String SELECT_BALANCE = "SELECT eventpoint_new FROM player WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
       
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement(SELECT_BALANCE);
            ps.setInt(1, p.id);
            conn.setAutoCommit(false);
            rs = ps.executeQuery();
            if (rs.next()) {
                int event_pointnew = rs.getInt("balance");
               
                    String UPDATE_ACCOUNT = "UPDATE player SET eventpoint_new=? WHERE id=?";
                    
                    ps = conn.prepareStatement(UPDATE_ACCOUNT);
                    ps.setInt(1, event_pointnew + sodiem);
                    ps.setInt(2, p.id);
                    ps.executeUpdate();
                    conn.commit();
                    ps.close();

                    
                    p.pointnew += sodiem;
                    
                
            }
            conn.close();
        
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    public static void PointEventLTN(Player p, int sodiem) {
        String SELECT_BALANCE = "SELECT event_ltn FROM player WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
       
        try {
            conn = DBService.gI().getConnection();
            ps = conn.prepareStatement(SELECT_BALANCE);
            ps.setInt(1, p.id);
            conn.setAutoCommit(false);
            rs = ps.executeQuery();
            if (rs.next()) {
                int event_ltn = rs.getInt("event_ltn");
               
                    String UPDATE_ACCOUNT = "UPDATE player SET event_ltn=? WHERE id=?";
                    
                    ps = conn.prepareStatement(UPDATE_ACCOUNT);
                    ps.setInt(1,  event_ltn + sodiem);
                    ps.setInt(2, p.id);
                    ps.executeUpdate();
                    conn.commit();
                    ps.close();

                    
                    p.event_ltn += sodiem;
                    
                
            }
            conn.close();
        
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
}
        
       
    

