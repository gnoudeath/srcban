package nro.item;

import nro.constant.Constant;
import nro.player.Player;
import nro.player.Detu;
import nro.main.Service;
import nro.main.Menu;
import nro.main.Util;
import nro.main.Server;
import nro.io.Message;
import nro.skill.Skill;
import java.util.Timer;
import java.util.TimerTask;
import nro.task.TaskService;
import nro.card.RadaCardService;
import nro.main.HelperDAO;
import nro.player.PlayerDAO;

public class useItem {
    public static void uesItem(Player p, Item item, int index , short id) {
        try {
            int numbagnull = p.getBagNull();
            if(item.template.type == (byte)7 && p._canUSEITEM)
            { //su dung sach vo cong
                p._canUSEITEM = false;
                if(item.template.gender == p.gender || (item.id >= 434 && item.id <= 440)) {
                    if((((item.id >= 66 && item.id <= 72) || (item.id >= 94 && item.id <= 100) || (item.id >= 115 && item.id <= 121) || (item.id >= 300 && item.id <= 313)
                    || (item.id >= 488 && item.id <= 501) || (item.id >= 434 && item.id <= 440)) && (p.gender == (byte)0)) ||
                    (((item.id >= 79 && item.id <= 86) || (item.id >= 101 && item.id <= 107) || (item.id >= 122 && item.id <= 128) || (item.id >= 328 && item.id <= 341)
                    || (item.id >= 481 && item.id <= 487) || (item.id >= 474 && item.id <= 480) || (item.id >= 434 && item.id <= 440)) && (p.gender == (byte)1)) ||
                    (((item.id >= 87 && item.id <= 93) || (item.id >= 108 && item.id <= 114) || (item.id >= 129 && item.id <= 135) || (item.id >= 314 && item.id <= 327)
                    || (item.id >= 502 && item.id <= 515) || (item.id >= 434 && item.id <= 440)) && (p.gender == (byte)2))) {
                        String idTempAndPoint = item.getIDTemplateAndLevelSkill(item.id);
                        String[] arrOfStr = idTempAndPoint.split(",", 3);
                        int _tempID = Integer.parseInt(arrOfStr[0]);
                        int _pointID = Integer.parseInt(arrOfStr[1]);
                        if(p.power >= (Integer.parseInt(arrOfStr[2])*1000000L)) {
                            Skill skillNew = p.nClass.getSkillTemplate(_tempID).skills[_pointID - 1];
                            boolean isUpSkill = false;
                            boolean isNew = true;
                            boolean chkNotSkillLearn = true;
                            for(int i = 0; i < p.skill.size(); i++) {
                                if(((p.skill.get(i).skillId + 1) == skillNew.skillId) && ((p.skill.get(i).point + 1) == skillNew.point)) {
                                    Skill newSKILL = new Skill();
                                    newSKILL.newSkill(skillNew);
                                    newSKILL.genderSkill = p.gender;
                                    p.skill.remove(i);
    //                                p.skill.add(skillNew);
                                    p.skill.add(newSKILL);
                                    isUpSkill = true;
                                    isNew = false;
                                }
                            }
                            for(int i = 0; i < p.listSkill.size(); i++) {
                                if(p.listSkill.get(i).skillId == _tempID) {
                                    Skill _skillnew = new Skill();
                                    _skillnew.skillId = (short)_tempID;
                                    _skillnew.point = _pointID;
                                    p.listSkill.remove(i);
                                    p.listSkill.add(_skillnew);
                                }
                            }
                            for(Skill skl: p.listSkill) {
                                if(skl.skillId == skillNew.skillId) {
                                    chkNotSkillLearn = false;
                                    break;
                                }
                            }
                            if(isNew && chkNotSkillLearn && 
                            ((p.gender == 0 && (skillNew.skillId == 7 || skillNew.skillId == 42 || skillNew.skillId == 63 || skillNew.skillId == 70 || skillNew.skillId == 128 || skillNew.skillId == 142 || skillNew.skillId == 121))
                            || (p.gender == 1 && (skillNew.skillId == 21 || skillNew.skillId == 49 || skillNew.skillId == 77 || skillNew.skillId == 84 || skillNew.skillId == 107 || skillNew.skillId == 114 || skillNew.skillId == 121))
                            || (p.gender == 2 && (skillNew.skillId == 35 || skillNew.skillId == 56 || skillNew.skillId == 91 || skillNew.skillId == 98 || skillNew.skillId == 135 || skillNew.skillId == 149 || skillNew.skillId == 121)))) {
    //                            Skill _skillnew = new Skill();
    //                            _skillnew.skillId = (short)_tempID;
    //                            _skillnew.point = _pointID;
    //                            p.skill.add(skillNew);
    //                            p.listSkill.add(_skillnew);
    //                            isUpSkill = true;

                                Skill _skillnew = new Skill();
                                Skill Skill = new Skill();

                                Skill _SKILLTEMP = p.nClass.getSkillTemplate((short)_tempID).skills[0];
                                _skillnew.template = _SKILLTEMP.template;
                                _skillnew.skillId = _SKILLTEMP.skillId;
                                _skillnew.point = _SKILLTEMP.point;
                                _skillnew.powRequire = _SKILLTEMP.powRequire;
                                _skillnew.coolDown = _SKILLTEMP.coolDown;
                                _skillnew.lastTimeUseThisSkill = 0;
                                _skillnew.dx = _SKILLTEMP.dx;
                                _skillnew.dy = _SKILLTEMP.dy;
                                _skillnew.maxFight = _SKILLTEMP.maxFight;
                                _skillnew.manaUse = _SKILLTEMP.manaUse;
                                _skillnew.options = _SKILLTEMP.options;
                                _skillnew.paintCanNotUseSkill = _SKILLTEMP.paintCanNotUseSkill;
                                _skillnew.damage = _SKILLTEMP.damage;
                                _skillnew.moreInfo = _SKILLTEMP.moreInfo;
                                _skillnew.price = _SKILLTEMP.price;
                                _skillnew.genderSkill = (byte)p.gender;
                                _skillnew.tempSkillId = (short)_SKILLTEMP.template.id;

                                p.skill.add(_skillnew);
                                //END SET UP SKILL
                                Skill.skillId = (short)_SKILLTEMP.template.id;
                                Skill.point = 1;
                                p.listSkill.add(Skill);
                                isUpSkill = true;
                            }
                            if(isUpSkill) {
                                //send update skill
                                Message m = null;
                                try {
                                    m = new Message(-30); //subcommand
                                    m.writer().writeByte(2); //type 
                                    m.writer().writeByte(p.skill.size()); // tong co skill da hoc
                                    for(int i = 0; i < p.skill.size(); i++) {
                                        m.writer().writeShort(p.skill.get(i).skillId); //skill id
                                    }
                                    m.writer().flush();
                                    p.session.sendMessage(m);
                                    m.cleanup();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(m != null) {
                                        m.cleanup();
                                    }
                                }
                                p.sendAddchatYellow("Bạn vừa học thành công " + item.template.name);
                                p.removeItemBag(index); //remove itemkhoi bag
                                Service.gI().updateItemBag(p);
                            } else {
                                p.sendAddchatYellow("Sách không phù hợp!");
                            }

                        } else {
                            p.sendAddchatYellow("Không đủ sức mạnh yêu cầu!");
                        }
                    } else {
                        p.sendAddchatYellow("Sách không phù hợp!");
                    }
                } else {
                    Service.gI().serverMessage(p.session, "Hành tinh không phù hợp!");
                }
                p._canUSEITEM = true;
                return;
            }
            else if(item.template.type == (byte)12)
            { //GOI RONG THAN
                if(item.id == 14 && item.quantity >= 1 && (p.map.id == 0 || p.map.id == 7 || p.map.id == 14)) { //RONG THAN TRAI DAT 1SAO
                    p._indexGiaoDich.clear();
                    p._indexGiaoDich.add((byte)index);
                    for(byte j = 2; j < 8; j++) {
                        for(byte i = 0; i < p.ItemBag.length; i++) {
                            if(p.ItemBag[i] != null && (p.ItemBag[i].template.id == ((int)j + 13)) && (p.ItemBag[i].quantity >= 1)) {
                                p._indexGiaoDich.add(i);
                                break;
                            }
                        }
                    }
                    if(p._indexGiaoDich.size() == 7) { //SEND GOI RONG THAN
                        //TRU SO LUONG NGOC RONG TRUOC KHI GOI RONG THAN
                        for(byte i = 0; i < p._indexGiaoDich.size(); i++) {
                            p.ItemBag[p._indexGiaoDich.get(i)].quantity -= 1;
                            if(p.ItemBag[p._indexGiaoDich.get(i)].quantity == 0) {
                                p.ItemBag[p._indexGiaoDich.get(i)] = null;
                            }
                        }
                        p._indexGiaoDich.clear();
                        Service.gI().updateItemBag(p);
                        
                        //SEND EFFECT CHO ALL MAP GOI RONG
                        Service.gI().sendEffectServer((byte)1, (byte)1, (byte)20, p.x, (short)(p.y+150), (short)1, p);
                        Message m = null;
                        try {
                            m = new Message(-83);
                            m.writer().writeByte(0);
                            m.writer().writeShort((short)p.map.id); //id map
                            m.writer().writeShort((short)p.map.bgId); //bg id map
                            m.writer().writeByte(p.zone.id); //khu vuc
                            m.writer().writeInt(p.id);
                            m.writer().writeUTF("");
                            m.writer().writeShort(p.x); //x rong
                            m.writer().writeShort(p.y);//y rong
                            m.writer().writeByte(0); //1 la rong namek
                            m.writer().flush();
                            for(byte i = 0; i < p.zone.players.size(); i++) {
                                if(p.zone.players.get(i) != null) {
                                    p.zone.players.get(i).session.sendMessage(m);
                                }
                            }
//                            for(Player _p: p.zone.players) {
//                                _p.session.sendMessage(m);
//                            }
                            m.cleanup();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if(m != null) {
                                m.cleanup();
                            }
                        }
                        //UI CHON DIEU UOC
                        p.menuID = -1;
                        p.menuNPCID = 24;
                        Menu.doMenuArray(p, 24,"Hãy mau chọn điều ước, hay ngươi muốn một chiếc quần như con lợn Uron kia?",new String[]{"Đẹp trai nhất\nvũ trụ","Găng đang mang\nlên 1 cấp","Đổi skill 2, 3\nđệ tử","Đổi skill 3, 4\nđệ tử", "Găng đệ tử đang\nmang lên 1 cấp","Quần đang mang\nlên 1 cấp","Giày đang mang\nlên 1 cấp", "200 Tr Sức\nmạnh, Tiềm năng","Chí mạng +2%"});
                    } else {
                        p.sendAddchatYellow("Hãy thu thập đủ 7 viên ngọc rồng để có thể ước");
                    }
                    p._indexGiaoDich.clear();
                } else {
                    p.sendAddchatYellow("Chỉ có thể gọi rồng thần ở làng");
                }
                return;
            } //ITEM DEO LUNG
            else if(item.template.type == (byte)11)
            {
//                if(item.id >= 995 || item.id == 954 || item.id == 955 || (item.id >= 467 && item.id <= 471) || (item.id >= 800 && item.id <= 804)) {
//                    if(p.itemLung == null || p.itemLung.id != item.id) {
//                        p.itemLung = item;
//                        Service.gI().loadPoint(p.session, p);
//                        if(p.imgNRSD != (byte)53 && p.imgNRSD != (byte)37) {
//                            Service.gI().setItemBagNew(p, item.id);
//                        }
//                    } else if(p.itemLung != null && p.itemLung.id == item.id) {
//                        p.itemLung = null;
//                        Service.gI().loadPoint(p.session, p);
//                        if(p.imgNRSD != (byte)53 && p.imgNRSD != (byte)37) {
//                            p.zone.resetBagClan(p);
//                        }
//                    }
//                }
                return;
            }
            else if(item.template.type == (byte)29 || item.template.type == (byte)31)
            {//MAY DO CAPSULE KY BI VA CAC ITEM BUFF
                boolean hasItem = p.isCanUseBuff(item.id);
                if(((item.id >= 663 && item.id <= 667) || item.id == 465 || item.id == 466 || item.id == 472 || item.id == 473) && p.hasThucAnBuff(item.id)) {
                    p.sendAddchatYellow("Chỉ được sử dụng 1 loại thức ăn");
                    return;
                }
                if(p.cItemBuff < (byte)5 || (p.cItemBuff == (byte)5 && hasItem)) {
                    if(!hasItem) {
                        p.cItemBuff = (byte)(p.cItemBuff + 1) > (byte)5 ? (byte)5 : (byte)(p.cItemBuff + 1);
                        p.idItemBuff.add(item.id);
                    }
                    if(item.id == 379) { //MAY DO CAPSULE
                        //send 30p máy dò capsule
                        p.updateQuantityItemBag(index, 1);
                        p.zone.showIconItemUse(p, 2758, 1800);

                        p.useMayDoCapsule = true;
                        p.timeEndCSKB = (System.currentTimeMillis() + 1800000);
                        // TASK END TDLT NEU HET TIME
                        Timer resetMayDo = new Timer();
                        TimerTask rsMayDo = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useMayDoCapsule = false;
                                p.timeEndCSKB = 0;
                                p.timerCSKB = null;
                                resetMayDo.cancel();
                            };
                        };
                        resetMayDo.schedule(rsMayDo, 1800000);
                        p.timerCSKB = resetMayDo;
                        return;
                    } else if(item.id == 381) { //USE CUONG NO
                        p.updateQuantityItemBag(index, 1);
                        p.zone.showIconItemUse(p, 2754, 600);

                        p.useCuongNo = true;
                        p.timeEndCN = (System.currentTimeMillis() + 600000);
                        if(p.timerCN != null) {
                            p.timerCN.cancel();
                        }

                        Service.gI().loadPoint(p.session, p);
                        // TASK END CUONG NO NEU HET TIME
                        Timer resetCN = new Timer();
                        TimerTask rsCN = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useCuongNo = false;
                                p.timeEndCN = 0;
                                p.timerCN = null;
                                Service.gI().loadPoint(p.session, p);
                                resetCN.cancel();
                            };
                        };
                        resetCN.schedule(rsCN, 600000);
                        p.timerCN = resetCN;
                        return;
                    } else if(item.id == 382) { //USE BO HUYET
                        p.updateQuantityItemBag(index, 1);
                        p.zone.showIconItemUse(p, 2755, 600);

                        p.useBoHuyet = true;
                        p.timeEndBH = (System.currentTimeMillis() + 600000);
                        if(p.timerBH != null) {
                            p.timerBH.cancel();
                        }
                        Service.gI().loadPoint(p.session, p);
                        // TASK END BO HUYET NEU HET TIME
                        Timer resetBH = new Timer();
                        TimerTask rsBH = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useBoHuyet = false;
                                p.timeEndBH = 0;
                                p.timerBH = null;
                                Service.gI().loadPoint(p.session, p);
                                resetBH.cancel();
                            };
                        };
                        resetBH.schedule(rsBH, 600000);
                        p.timerBH = resetBH;
                    } else if(item.id == 383) { //USE BO KHI
                        p.updateQuantityItemBag(index, 1);
                        p.zone.showIconItemUse(p, 2756, 600);

                        p.useBoKhi = true;
                        p.timeEndBK = (System.currentTimeMillis() + 600000);
                        if(p.timerBK != null) {
                            p.timerBK.cancel();
                        }
                        Service.gI().loadPoint(p.session, p);
                        // TASK END BO KHI NEU HET TIME
                        Timer resetBK = new Timer();
                        TimerTask rsBK = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useBoKhi = false;
                                p.timeEndBK = 0;
                                p.timerBK = null;
                                Service.gI().loadPoint(p.session, p);
                                resetBK.cancel();
                            };
                        };
                        resetBK.schedule(rsBK, 600000);
                        p.timerBK = resetBK;
                    } else if(item.id == 384) { //USE GIAP XEN
                        p.updateQuantityItemBag(index, 1);
                        p.zone.showIconItemUse(p, 2757, 600);

                        p.useGiapXen = true;
                        p.timeEndGX = (System.currentTimeMillis() + 600000);
                        if(p.timerGX != null) {
                            p.timerGX.cancel();
                        }
                        // TASK END GIAP XEN NEU HET TIME
                        Timer resetGX = new Timer();
                        TimerTask rsGX = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useGiapXen = false;
                                p.timeEndGX = 0;
                                p.timerGX = null;
                                resetGX.cancel();
                            };
                        };
                        resetGX.schedule(rsGX, 600000);
                        p.timerGX = resetGX;
                    } else if(item.id == 385) { //USE AN DANH TODO:// UPDATE SAU
                        p.updateQuantityItemBag(index, 1);
                        p.zone.showIconItemUse(p, 2760, 600);
                    } else if(item.id == 1016 || item.id == 1017) { //USE THUOC MO
                        p.updateQuantityItemBag(index, 1);
                        int timeThuoc = 600000;
                        if(item.id == 1017) {
                            p.zone.showIconItemUse(p, 9068, 1800);
                            timeThuoc = 1800000;
                        } else {
                            p.zone.showIconItemUse(p, 9068, 600);
                        }

                        p.useThuocMo = true;
                        p.timeEndTM = (System.currentTimeMillis() + (long)timeThuoc);
                        if(p.timerTM != null) {
                            p.timerTM.cancel();
                        }

                        Service.gI().loadPoint(p.session, p);

                        Timer resetTM = new Timer();
                        TimerTask rsTM = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useThuocMo = false;
                                p.timeEndTM = 0;
                                p.timerTM = null;
                                Service.gI().loadPoint(p.session, p);
                                resetTM.cancel();
                            };
                        };
                        resetTM.schedule(rsTM, timeThuoc);
                        p.timerTM = resetTM;
                        //CHECK NHIEM VU THUOC MO
                        if(p.taskId == (byte)32 && p.crrTask.index == (byte)3) {
                            TaskService.gI().updateCountTask(p);
                        }
                        return;
                    } else if((item.id >= 663 && item.id <= 667) || item.id == 465 || item.id == 466 || item.id == 472 || item.id == 473) { //USE THUC AN
                        p.updateQuantityItemBag(index, 1);
                        int timeThucAn = 600000;
                        if(item.id >= 663 && item.id <= 667) {
                            p.zone.showIconItemUse(p, (item.id + 5661), 600);
                            if(p.havePet == 1) {
                                p.detu.useThucAn = true;
                            }
                            p.timeEndTA = (System.currentTimeMillis() + 600000);
                        } else if(item.id == 465){
                            p.zone.showIconItemUse(p, (item.id + 3577), 3600);
                            p.timeEndTA = (System.currentTimeMillis() + 3600000);
                            timeThucAn = 3600000;
                        } else if(item.id == 466){
                            p.zone.showIconItemUse(p, (item.id + 3577), 5400);
                            p.timeEndTA = (System.currentTimeMillis() + 5400000);
                            timeThucAn = 5400000;
                        } else if(item.id == 472){
                            p.zone.showIconItemUse(p, (item.id + 3653), 7200);
                            p.timeEndTA = (System.currentTimeMillis() + 7200000);
                            timeThucAn = 7200000;
                        } else if(item.id == 473){
                            p.zone.showIconItemUse(p, (item.id + 3653), 9000);
                            p.timeEndTA = (System.currentTimeMillis() + 9000000);
                            timeThucAn = 9000000;
                        }

                        p.useThucAn = true;
                        p.idTAUse = item.id;
                        
                        if(p.timerTA != null) {
                            p.timerTA.cancel();
                        }

                        Service.gI().loadPoint(p.session, p);
                        Timer resetTA = new Timer();
                        TimerTask rsTA = new TimerTask() {
                            public void run()
                            {
                                p.cItemBuff = (byte)(p.cItemBuff - 1) < (byte)0 ? (byte)0 : (byte)(p.cItemBuff - 1);
                                p.removeIdBuff(item.id);
                                p.useThucAn = false;
                                if(p.havePet == 1) {
                                    p.detu.useThucAn = false;
                                }
                                p.timerTA = null;
                                Service.gI().loadPoint(p.session, p);
                                resetTA.cancel();
                            };
                        };
                        resetTA.schedule(rsTA, timeThucAn);
                        p.timerTA = resetTA;
                    }
                } else {
                    p.sendAddchatYellow("Sử dụng tối đa 5 món");
                }
                return;
            } //ITEM BAY VIP
            else if(item.template.type == (byte)24)
            {
                if (p.ItemBody[9] != null) {
                    p.ItemBag[index] = p.ItemBody[9];
                } else {
                    p.ItemBag[index] = null;
                }
                p.ItemBody[9] = item;
                Service.gI().updateItemBag(p);
                Service.gI().updateItemBody(p);
                p.LOADCAITRANGTOME();
                return;
            }
            else if(item.template.type == (byte)25)
            { //RADA RO NGOC RONG
                if(item.id == 361 && p.ItemBag[(byte)index].quantity >= 1) {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Service.gI().updateItemBag(p);
                    //UI RADA RO NGOC RONG
                    p.menuID = -1;
                    p.menuNPCID = 99;
                    p.idNrNamecGo = (byte)Util.nextInt(0, 7);
                    Menu.doMenuArray(p, 24,"1 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[0] +")" + (Server.gI().pNrNamec[0] != "" ? "("+Server.gI().pNrNamec[0]+")" : "") +"\b2 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[1] +")" + (Server.gI().pNrNamec[1] != "" ? "("+Server.gI().pNrNamec[1]+")" : "") +"\b3 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[2] +")" + (Server.gI().pNrNamec[2] != "" ? "("+Server.gI().pNrNamec[0]+")" : "") +"\b4 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[3] +")" + (Server.gI().pNrNamec[3] != "" ? "("+Server.gI().pNrNamec[3]+")" : "") +"\b5 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[4] +")" + (Server.gI().pNrNamec[4] != "" ? "("+Server.gI().pNrNamec[4]+")" : "") +"\b6 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[5] +")" + (Server.gI().pNrNamec[5] != "" ? "("+Server.gI().pNrNamec[5]+")" : "") +"\b7 Sao:Namếc ( ? m)(" + Server.gI().nameNrNamec[6] +")" + (Server.gI().pNrNamec[6] != "" ? "("+Server.gI().pNrNamec[6]+")" : ""),new String[]{"Đến ngay\nViên "+(byte)(p.idNrNamecGo + 1)+" Sao\n10 ngọc","Đến ngay\nViên "+(byte)(p.idNrNamecGo + 1)+" Sao\n100k vàng","Kết thúc"});
                    return;
                }
            } // UPDATE SKILL DE TU
            else if(item.template.type == (byte)27)
            {
                if((item.id == 402 || item.id == 403 || item.id == 404 || item.id == 759) && p._canUSEITEM)
                {
                    p._canUSEITEM = false;
                    if(p.detu != null && p.havePet == (byte)1)
                    {
                        boolean isUse = false;
                        short idOld = -1;
                        byte genderOld = -1;
                        short tempOld = -1;
                        if(p.detu.listSkill.get(0).point < 7 && item.id == 402) {
                            genderOld = p.detu.listSkill.get(0).genderSkill;
                            tempOld = p.detu.listSkill.get(0).tempSkillId;
                            if(tempOld == (short)0) {
                                idOld = (short)0;
                            } else if(tempOld == (short)2) {
                                idOld = (short)14;
                            } else if(tempOld == (short)4) {
                                idOld = (short)28;
                            } else {
                                idOld = (short)14;
                            }
                            p.detu.listSkill.get(0).point = (p.detu.listSkill.get(0).point + 1) > 7 ? 7 : (p.detu.listSkill.get(0).point + 1);
                            p.detu.listSkill.get(0).skillId = ((short)(p.detu.listSkill.get(0).skillId + (short)1) >= idOld && (short)(p.detu.listSkill.get(0).skillId + (short)1) <= (short)(idOld + 6)) ? (short)(p.detu.listSkill.get(0).skillId + (short)1) : (short)(idOld + (short)6);
                            isUse = true;
                        } else if(p.detu.listSkill.get(1).point < 7 && item.id == 403 && p.detu.power >= 150000000) {
                            genderOld = p.detu.listSkill.get(1).genderSkill;
                            tempOld = p.detu.listSkill.get(1).tempSkillId;
                            if(tempOld == (short)1) {
                                idOld = (short)7;
                            } else if(tempOld == (short)3) {
                                idOld = (short)21;
                            } else if(tempOld == (short)5) {
                                idOld = (short)35;
                            } else {
                                idOld = (short)21;
                            }
                            p.detu.listSkill.get(1).point = (p.detu.listSkill.get(1).point + 1) > 7 ? 7 : (p.detu.listSkill.get(1).point + 1);
                            p.detu.listSkill.get(1).skillId = ((short)(p.detu.listSkill.get(1).skillId + (short)1) >= idOld && (short)(p.detu.listSkill.get(1).skillId + (short)1) <= (short)(idOld + 6)) ? (short)(p.detu.listSkill.get(1).skillId + (short)1) : (short)(idOld + (short)6);
                            isUse = true;
                        } else if(p.detu.listSkill.get(2).point < 7 && item.id == 404 && p.detu.power >= 1500000000L) {
                            genderOld = p.detu.listSkill.get(2).genderSkill;
                            tempOld = p.detu.listSkill.get(2).tempSkillId;
                            if(tempOld == (short)6) {
                                idOld = (short)42;
                            } else if(tempOld == (short)9) {
                                idOld = (short)63;
                            } else if(tempOld == (short)8) {
                                idOld = (short)56;
                            } else {
                                idOld = (short)63;
                            }
                            p.detu.listSkill.get(2).point = (p.detu.listSkill.get(2).point + 1) > 7 ? 7 : (p.detu.listSkill.get(2).point + 1);
                            p.detu.listSkill.get(2).skillId = ((short)(p.detu.listSkill.get(2).skillId + (short)1) >= idOld && (short)(p.detu.listSkill.get(2).skillId + (short)1) <= (short)(idOld + 6)) ? (short)(p.detu.listSkill.get(2).skillId + (short)1) : (short)(idOld + (short)6);
                            isUse = true;
                        } else if(p.detu.listSkill.get(3).point < 7 && item.id == 759 && p.detu.power >= 20000000000L) {
                            genderOld = p.detu.listSkill.get(3).genderSkill;
                            tempOld = p.detu.listSkill.get(3).tempSkillId;
                            if(tempOld == (short)13) {
                                idOld = (short)91;
                            } else if(tempOld == (short)19) {
                                idOld = (short)121;
                            } else {
                                idOld = (short)121;
                            }
                            p.detu.listSkill.get(3).point = (p.detu.listSkill.get(3).point + 1) > 7 ? 7 : (p.detu.listSkill.get(3).point + 1);
                            p.detu.listSkill.get(3).skillId = ((short)(p.detu.listSkill.get(3).skillId + (short)1) >= idOld && (short)(p.detu.listSkill.get(3).skillId + (short)1) <= (short)(idOld + 6)) ? (short)(p.detu.listSkill.get(3).skillId + (short)1) : (short)(idOld + (short)6);
                            isUse = true;
                        }
                        if(isUse) {
                            p.zone.chat(p.detu, "Ngon võ công anh mạnh hơn rồi đấy!");
                            p.updateQuantityItemBag(index, 1);
        //                    p.removeItemBag(index);
        //                    Service.gI().updateItemBag(p);
                        }
                    }
                    else
                    {
                        p.sendAddchatYellow("Không thể thực hiện");
                    }
                    p._canUSEITEM = true;
                    return;
                } //DDOI DE TU
                else if(item.id == 401)
                {
                    if(p.detu != null && p.havePet == (byte)1)
                    {
                        if(p.petfucus == 1) {
                            p.zone.leaveDEEEEE(p.detu); //remove detu
                        } else {
                            p.petfucus = 1;
                        }
                        p.statusPet = 0;
                        p.detu = null;
                        p.detu = new Detu();
                        p.detu.initDetuBroly(p.detu);
                        p.detu.id = (-100000 - p.id);
                        if(p.NhapThe == 0) {
                            p.zone.pets.add(p.detu);
                        }
                        p.detu.x = (short)(p.x - (short)50);
                        p.detu.y = (short)(p.y - (short)50);

                        //NEU LOAD DE TU O MAP COOL
                        if(p.map.MapCold()) {
                            p.zone.upDownPointPETMapCool(p);
                        }
                        //NEU LOAD DE TU O MAP COOL
                        if(p.NhapThe == 0) {
                            for(byte i = 0; i < p.zone.players.size(); i++) {
                                if(p.zone.players.get(i) != null) {
                                    p.zone.loadInfoDeTu(p.zone.players.get(i).session, p.detu);
                                }
                            }
                            //                    for(Player _p: p.zone.players) {
                            //                        p.zone.loadInfoDeTu(_p.session, p.detu);
                            //                    }
                        }
                        p.updateQuantityItemBag(index, 1);
                    }
                    else
                    {
                        p.sendAddchatYellow("Không thể thực hiện");
                    }
                    return;
                } //TU DONG LUYEN TAP
                else if (item.id == 521)
                {
                    if(p.timeEndTDLT == 0)
                    {
                        int _timeP = item.getParamItemByID(1); //so phut tu dong luyen tap
                        p.timeEndTDLT = (long)(System.currentTimeMillis() + (long)(_timeP*60000));

                        for(byte i = 0; i < p.ItemBag[(byte)index].itemOptions.size(); i++) {
                            if(p.ItemBag[(byte)index].itemOptions.get(i).id == 1) {
                                p.ItemBag[(byte)index].itemOptions.get(i).param = 0;
                            }
                        }
                        Service.gI().updateItemBag(p);
                        p.zone.showIconItemUse(p, 4387, 3600);

                        //SEND AUTOPLAY
                        Message m = null;
                        try {
                            m = new Message(-116);
                            m.writer().writeByte(1);
                            m.writer().flush();
                            p.session.sendMessage(m);
                            m.cleanup();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if(m != null) {
                                m.cleanup();
                            }
                        }

                        // TASK END TDLT NEU HET TIME
                        Timer resetTDLT = new Timer();
                        TimerTask rsTDLT = new TimerTask() {
                            public void run()
                            {
                                if(p.timeEndTDLT == 0) {
                                    resetTDLT.cancel();
                                }
                                Message m = null;
                                try {
                                    m = new Message(-116);
                                    m.writer().writeByte(0);
                                    m.writer().flush();
                                    p.session.sendMessage(m);
                                    m.cleanup();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(m != null) {
                                        m.cleanup();
                                    }
                                }
                            };
                        };
                        resetTDLT.schedule(rsTDLT, (long)(_timeP*60000));
                    }
                    else
                    {
                        int _timeConLai = (int)((p.timeEndTDLT - System.currentTimeMillis())/60000);
                        p.timeEndTDLT = 0;
                        for(byte i = 0; i < p.ItemBag[(byte)index].itemOptions.size(); i++) {
                            if(p.ItemBag[(byte)index].itemOptions.get(i).id == 1) {
                                p.ItemBag[(byte)index].itemOptions.get(i).param = _timeConLai;
                            }
                        }

                        Service.gI().updateItemBag(p);
                        p.zone.showIconItemUse(p, 4387, 0);
                        //SEND END TU DONG LUYEN TAP
                        Message m = null;
                        try {
                            m = new Message(-116);
                            m.writer().writeByte(0);
                            m.writer().flush();
                            p.session.sendMessage(m);
                            m.cleanup();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if(m != null) {
                                m.cleanup();
                            }
                        }
                    }
                    return;
                } //USE CAPSULE KIBI
                else if(item.id == 380 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }

                    int rdIDITEM = Util.nextInt(381, 386);
                    short IDICONITEMBUFF = 2760;
                    if(rdIDITEM == 381) {
                        IDICONITEMBUFF = (short)2754;
                    } else if(rdIDITEM == 382) {
                        IDICONITEMBUFF = (short)2755;
                    } else if(rdIDITEM == 383) {
                        IDICONITEMBUFF = (short)2756;
                    } else if(rdIDITEM == 384) {
                        IDICONITEMBUFF = (short)2757;
                    }
                    Service.gI().sendOpenItem(p, IDICONITEMBUFF);

                    Item _itemBuffRandom = ItemSell.getItemNotSell(rdIDITEM);
                    Item _ITEMBUFF = new Item(_itemBuffRandom);
//                    _AVATARVIP.itemOptions.add(new ItemOption(77, Util.nextInt(15, 21)));
                    p.addItemToBag(_ITEMBUFF);
                    Service.gI().updateItemBag(p);
                    return;
}else if(item.id == 399 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Item tct = ItemSell.getItemNotSell(399);
                    Item tv = ItemSell.getItemNotSell(457);
                    int t = Util.nextInt(15, 70);
                    tv.quantity =t;
                    p.addItemToBag(tv);
                    Service.gI().updateItemBag(p);
                   
                    Service.gI().buyDone(p);
                    p.sendAddchatYellow("Chúc Mừng Bạn Vừa Nhận Được " +t+" "+tv.template.name +" từ "+tct.template.name);
                     Service.gI().sendThongBaoServer("Chúc Mừng Người Chơi "+p.name+" Nhận được " +t+" "+tv.template.name +" từ "+tct.template.name);
                    return;
                }else if(item.id == 397 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Item tv = ItemSell.getItemNotSell(457);
                    Item cn = ItemSell.getItemNotSell(381);
                    Item bh = ItemSell.getItemNotSell(382);
                    Item gx = ItemSell.getItemNotSell(383);
                    tv.quantity =10;
                    cn.quantity =10;
                    bh.quantity =10;
                    gx.quantity =10;                   
                    int rd = Util.nextInt(0,5); //RANDOM TU 0,1,2,3,4
                    int idItemKichHoat[][] = {{0, 6, 21, 27, 12}, {1, 7, 22, 28, 12}, {2, 8, 23, 29, 12}};
                    ItemSell ao = ItemSell.getItemSell(idItemKichHoat[(int) p.gender][0], (byte) 0);
                    ItemSell quan = ItemSell.getItemSell(idItemKichHoat[(int) p.gender][1], (byte) 0);
                    ItemSell gang = ItemSell.getItemSell(idItemKichHoat[(int) p.gender][2], (byte) 0);
                    ItemSell giay = ItemSell.getItemSell(idItemKichHoat[(int) p.gender][3], (byte) 0);
                    ItemSell rada = ItemSell.getItemSell(idItemKichHoat[(int) p.gender][4], (byte) 0);
                    Item aoop = new Item(ao.item);
                    aoop.itemOptions.add(new ItemOption(107, 3));
                   aoop.itemOptions.add(new ItemOption(30, 0)); //OPTION KHONG THE GIAO DICH
                    Item quanop = new Item(quan.item);
                    quanop.itemOptions.add(new ItemOption(107, 3));
                   quanop.itemOptions.add(new ItemOption(30, 0)); //OPTION KHONG THE GIAO DICH
                    Item gangop = new Item(gang.item);
                    gangop.itemOptions.add(new ItemOption(107, 3));
                   gangop.itemOptions.add(new ItemOption(30, 0)); //OPTION KHONG THE GIAO DICH
                   Item giayop = new Item(giay.item);
                    giayop.itemOptions.add(new ItemOption(107, 3));
                   giayop.itemOptions.add(new ItemOption(30, 0)); //OPTION KHONG THE GIAO DICH
                   Item radaop = new Item(rada.item);
                    radaop.itemOptions.add(new ItemOption(107, 3));
                   radaop.itemOptions.add(new ItemOption(30, 0)); //OPTION KHONG THE GIAO DICH
                   p.addItemToBag(tv);
                   p.addItemToBag(cn);
                   p.addItemToBag(bh);
                   p.addItemToBag(gx);
                   p.addItemToBag(aoop);
                   p.addItemToBag(quanop);
                   p.addItemToBag(gangop);
                   p.addItemToBag(giayop);
                   p.addItemToBag(radaop);
                   p.sendAddchatYellow("nhận thành công 10 " +tv.template.name);
                    p.sendAddchatYellow("nhận thành công 10 " +cn.template.name);
                     p.sendAddchatYellow("nhận thành công 10 " +bh.template.name);
                      p.sendAddchatYellow("nhận thành công 10 " +gx.template.name);
                       p.sendAddchatYellow("nhận thành công  " +aoop.template.name+" 3sao");
                        p.sendAddchatYellow("nhận thành công  " +quanop.template.name+" 3sao");
                         p.sendAddchatYellow("nhận thành công  " +gangop.template.name+" 3sao");
                          p.sendAddchatYellow("nhận thành công " +giayop.template.name+" 3sao");
                           p.sendAddchatYellow("nhận thành công  " +radaop.template.name+" 3sao");
                       Service.gI().updateItemBag(p);
                    
                   
                    return;
                } 
//                }//USE TRUNG MABU
//                else if(item.id == 568 && p.ItemBag[(byte)index].quantity >= 1)
//                { 
//                    p.ItemBag[(byte)index].quantity -= 1;
//                    if(p.ItemBag[(byte)index].quantity == 0) {
//                        p.ItemBag[(byte)index] = null;
//                    }
//                    Service.gI().updateItemBag(p);
//                    p.hasTrungMabu = true;
//                                                p.sendAddchatYellow("Bạn vừa nhận được đệ tử Mabư, quay về nhà gặp Ông Già để thao tác");
//                } //USE THOI VANG
                else if(item.id == 457 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Service.gI().updateItemBag(p);
                    long _vang = ((long)p.vang + 0) > 2000000000L ? 2000000000L : ((long)p.vang + 000000000);
                    p.vang = _vang;
                    Service.gI().buyDone(p);
                    return;
                }else if(item.id == 1101&& p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                        int listvp[]={1001, 1007,1100,897,457,1107,382,381,20,16,17,18,19,383,384,838,1118,1039,15,1040,956};
                      int rdvp=Util.nextInt(0, 22);
                      Item vp = ItemSell.getItemNotSell(listvp[rdvp]);
                      p.addItemToBag(vp);
                      p.sendAddchatYellow("bạn nhận được "+ vp.template.name);
                       Service.gI().updateItemBag(p);
                    return;
                }else if(item.id == 722 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                        int listvp[]={1001, 1007,1100,897,457,220,221,222,223,224,16,17,18,19,20,17,878,1040,573,1039,859,956};
                      int rdvp=Util.nextInt(0,22);
                      Item vp = ItemSell.getItemNotSell(listvp[rdvp]);
                      p.addItemToBag(vp);
                      p.sendAddchatYellow("bạn nhận được "+ vp.template.name);
                      Service.gI().updateItemBag(p);
                    return;
                } //USE PET  //USE TAT NOEL
                else if(item.id == 1098 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 100);
                    int id_gift = Util.nextInt(220, 224); //random da nang cap
                    if(type_gift < 18) {//3,7s
                        id_gift = Util.nextInt(16, 20);
                    } else if(type_gift < 36) {//cdv ngũ sắc
                        id_gift = 733;
                    } else if(type_gift < 92) {//chichi 18 bluma
                        id_gift = Util.nextInt(1041, 1043);
                    } else if(type_gift < 95) {//thoi vang
                        id_gift = 457;
                    } else {//20 da nang cap

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 220 && id_gift <= 224) {
                        _itemEvent.quantity = 15;
                    } else if(id_gift == 457) {
                        _itemEvent.quantity = Util.nextInt(1, 13);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 733)
                    {
                        int dayEvent = Util.nextInt(0, 3);
                        if(dayEvent == 0) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 30));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 30*86400000;
                        } else if(dayEvent == 1) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 90));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 90*86400000;
                        } else if(dayEvent == 2) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 365));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 365*86400000;
                        }
                    }
                    else if(id_gift == 822)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 9) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(15, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(15, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(15, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if (itemEvent.isPet2())
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 8) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(5, 11)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(5, 11)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(5, 11)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1155)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 98) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1151)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 98) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1152)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 98) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;
                } //USE PET  //U LA TR
                else if(item.id == 1169 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 50);
                    int id_gift = Util.nextInt(1066, 1070); //random da nang cap
                    if(type_gift < 7) {//3,7s
                        id_gift = Util.nextInt(15, 18);
                    } else if(type_gift < 46) {//cdv ngũ sắc
                        id_gift = 1100;
                    } else if(type_gift < 46) {//cdv ngũ sắc
                        id_gift = 1110;
                    } else if(type_gift < 28) {//chichi 18 bluma
                        id_gift = 1090;
                    } else if(type_gift < 35) {//chichi 18 bluma
                        id_gift = 1091;
                    } else if(type_gift < 14) {//thoi vang
                        id_gift = 457;
                    } else {//20 da nang cap

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 1066 && id_gift <= 1070) {
                        _itemEvent.quantity = Util.nextInt(1, 30);
                    } else if(id_gift == 457) {
                        _itemEvent.quantity = Util.nextInt(1, 25);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 1100)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 49) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1111)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(11, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(11, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(11, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(108, 10));
                        if(Util.nextInt(0, 100) < 49) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if (itemEvent.isPet2())
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 7) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(5, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(5, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(5, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 19)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 19)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 19)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 10));
                        if(Util.nextInt(0, 100) < 49) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1091)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 46) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 47)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 42)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 42)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 42)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(108, Util.nextInt(1, 16)));
                        if(Util.nextInt(0, 100) < 49) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1090)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 46) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(25, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 47)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(25, 42)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(25, 42)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(25, 42)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, Util.nextInt(1, 16)));
                        if(Util.nextInt(0, 100) < 49) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;
                    
                } //USE PET  //USE TAT NOEL
                else if(item.id == 1165 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.event_ltn +=20;
                   PlayerDAO.updateDB(p);
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 50);
                    int id_gift = Util.nextInt(1066, 1070); //random da nang cap
                    if(type_gift < 3) {//1,7s
                        id_gift = Util.nextInt(14, 18);
                    } else if(type_gift < 14) {//cdv ngũ sắc
                        id_gift = 1110;
                    } else if(type_gift < 21) {//chichi 18 bluma
                        id_gift = 1008;
                    } else if(type_gift < 35) {//chichi 18 bluma
                        id_gift = 1146;
                     } else if(type_gift < 42) {//chichi 18 bluma
                        id_gift = 1164;
                    } else if(type_gift < 28) {//thoi vang
                        id_gift = 457;
                    } else {//20 da nang cap

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 1066 && id_gift <= 1070) {
                        _itemEvent.quantity = Util.nextInt(5, 30);
                    } else if(id_gift == 457) {
                        _itemEvent.quantity = Util.nextInt(5, 25);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 1110)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,50) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 26)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 26)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 26)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 15));
                        if(Util.nextInt(0, 50) < 28) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if (id_gift == 1008)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,50) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 10));
                        if(Util.nextInt(0, 50) < 28) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1146)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 50) < 35) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(117, Util.nextInt(1, 11)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, Util.nextInt(1, 16)));
                        if(Util.nextInt(0, 50) < 21) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1164)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 50) < 42) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 47)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(108, Util.nextInt(5, 20)));
                        if(Util.nextInt(0, 50) < 21) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;    
                   
                } //USE PET  //USE TAT NOEL
                else if(item.id == 1168 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.event_ltn +=20;
                   PlayerDAO.updateDB(p);
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 51);
                    int id_gift = Util.nextInt(1066, 1070); //random da nang cap
                    if(type_gift < 7) {//3,7s
                        id_gift = Util.nextInt(14, 18);
                    } else if(type_gift < 14) {//cdv ngũ sắc
                        id_gift = 1110;
                    } else if(type_gift < 21) {//chichi 18 bluma
                        id_gift = 1008;
                    } else if(type_gift < 35) {//chichi 18 bluma
                        id_gift = 1148;
                    } else if(type_gift < 42) {//chichi 18 bluma
                        id_gift = 904;
                    } else if(type_gift < 28) {//thoi vang
                        id_gift = 457;
                    } else {//20 da nang cap

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 1066 && id_gift <= 1070) {
                        _itemEvent.quantity = Util.nextInt(5, 30);
                    } else if(id_gift == 457) {
                        _itemEvent.quantity = Util.nextInt(2, 25);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 1110)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 15));
                        if(Util.nextInt(0, 50) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if (id_gift == 1114)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 28) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 10));
                        if(Util.nextInt(0, 50) < 14) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1148)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 35) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(117, Util.nextInt(1, 11)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, Util.nextInt(1, 16)));
                        if(Util.nextInt(0, 50) < 21) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 904)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 42) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 47)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, Util.nextInt(5, 20)));
                        if(Util.nextInt(0, 50) < 35) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                  
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;
                    
                } //USE PET  //USE TAT NOEL
                else if(item.id == 1166 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.event_ltn +=20;
                   PlayerDAO.updateDB(p);
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 50);
                    int id_gift = Util.nextInt(1066, 1070); //random da nang cap
                    if(type_gift < 3) {//3,7s
                        id_gift = Util.nextInt(14, 18);
                    } else if(type_gift < 14) {//cdv ngũ sắc
                        id_gift = 1110;
                    } else if(type_gift < 21) {//chichi 18 bluma
                        id_gift = 1008;
                    } else if(type_gift < 35) {//chichi 18 bluma
                        id_gift = 1149;
                    } else if(type_gift < 42) {//chichi 18 bluma
                        id_gift = 1155;
                    } else if(type_gift < 28) {//thoi vang
                        id_gift = 457;
                    } else {//20 da nang cap

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 1066 && id_gift <= 1070) {
                        _itemEvent.quantity = Util.nextInt(5,30);
                    } else if(id_gift == 457) {
                        _itemEvent.quantity = Util.nextInt(2, 25);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 1110)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 26)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 26)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 26)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 15));
                        if(Util.nextInt(0, 50) < 14) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if (id_gift == 1107)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, 10));
                        if(Util.nextInt(0, 50) < 21) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1149)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 35) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(117, Util.nextInt(1, 11)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(14, Util.nextInt(1, 16)));
                        if(Util.nextInt(0, 50) < 21) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1155)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 42) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(30, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(30, 47)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 47)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, Util.nextInt(5, 20)));
                        if(Util.nextInt(0, 50) < 21) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    
                   
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;
                    
                } //USE PET  //USE TAT NOEL
                else if(item.id == 1170 || item.id == 1167 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 100);
                    int id_gift = Util.nextInt(220, 224); //random da nang cap
                    if(type_gift < 18) {//3,7s
                        id_gift = Util.nextInt(16, 20);
                    } else if(type_gift < 36) {//cdv ngũ sắc
                        id_gift = Util.nextInt(1021, 1023);
                    } else if(type_gift < 92) {//chichi 18 bluma
                        id_gift = Util.nextInt(1087, 1089);
                    } else if(type_gift < 95) {//thoi vang
                        id_gift = 457;
                    } else {//20 da nang cap

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 220 && id_gift <= 224) {
                        _itemEvent.quantity = Util.nextInt(15, 35);
                    } else if(id_gift == 457) {
                        _itemEvent.quantity = Util.nextInt(1, 15);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 1021)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 9) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1022)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 9) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(11, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(11, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(11, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1023)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 9) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(11, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(11, 21)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(11, 21)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if (itemEvent.isPet2())
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0,10) < 35) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(5, 11)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(5, 11)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(5, 11)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 16)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 16)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(93, 7));
                            _itemEvent.timeHSD = System.currentTimeMillis() + 7*86400000;
                        }
                    }
                    else if(id_gift == 1089)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(108, 10));
                        if(Util.nextInt(0, 100) < 98) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1088)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        } else {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 36)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 36)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 100) < 98) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    else if(id_gift == 1087)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 100) < 95) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 41)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 41)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 41)));
                            _itemEvent.itemOptions.add(new ItemOption(108, Util.nextInt(5, 15)));
                            _itemEvent.itemOptions.add(new ItemOption(94, Util.nextInt(5, 15)));
                        } else {
                             _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(20, 41)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(20, 41)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(20, 41)));
                            _itemEvent.itemOptions.add(new ItemOption(108, Util.nextInt(5, 15)));
                            _itemEvent.itemOptions.add(new ItemOption(94, Util.nextInt(5, 15)));
                            _itemEvent.itemOptions.add(new ItemOption(14, 10));
                        }
                        if(Util.nextInt(0, 100) < 98) {
                            int dayrd = Util.nextInt(3, 8);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;
                } //USE PET  //Rương Tù Tội
                else if(item.id == 1009 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    //random
                    int type_gift = Util.nextInt(0, 10);
                    int id_gift = Util.nextInt(1066, 1070); //mảnh trang bị
                    
                    if(type_gift < 6) {//cumber vàng
                        id_gift = 1154;
                    } else {//30 mảnh 

                    }
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);
                    if(id_gift >= 1066 && id_gift <= 1070) {
                        _itemEvent.quantity = Util.nextInt(1, 50);
                    } else {
                        _itemEvent.quantity = 1;
                    }
                    
                    if(id_gift == 1154)
                    {
                        _itemEvent.itemOptions.clear();
                        if(Util.nextInt(0, 10) < 6) {
                            _itemEvent.itemOptions.add(new ItemOption(50, Util.nextInt(10, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(77, Util.nextInt(10, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(103, Util.nextInt(10, 46)));
                            _itemEvent.itemOptions.add(new ItemOption(117, Util.nextInt(1, 10)));
                        }
                        _itemEvent.itemOptions.add(new ItemOption(94, 10));
                        if(Util.nextInt(0, 10) < 10) {
                            int dayrd = Util.nextInt(3, 15);
                            _itemEvent.itemOptions.add(new ItemOption(93, dayrd));
                            _itemEvent.timeHSD = System.currentTimeMillis() + dayrd*86400000;
                        }
                    }
                    p.addItemToBag(_itemEvent);
                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name);
                    return;
                } //USE HOP QUA NOEL
                else if(item.id == 648 && p.ItemBag[(byte)index].quantity >= 1)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    
                    int id_gift = Util.nextInt(386, 395);
                    Item itemEvent =  ItemSell.getItemNotSell(id_gift);
                    Item _itemEvent = new Item(itemEvent);

                    _itemEvent.timeHSD = System.currentTimeMillis() + 864000000;
                    p.addItemToBag(_itemEvent);

                    p.vang = (p.vang + Constant.GOLD_NOEL) > Constant.MAX_MONEY_OLD ? Constant.MAX_MONEY_OLD : (p.vang + Constant.GOLD_NOEL);
                    Service.gI().buyDone(p);

                    Service.gI().updateItemBag(p);
                    p.sendAddchatYellow("Bạn nhận được x" + _itemEvent.quantity + " " + itemEvent.template.name + ", 30Tr vàng");
                    return;
                }
                else if(item.id == 1008 || item.id == 967 || item.id == 944 || item.id == 943 || item.id == 942 || item.id == 936 ||
                        item.id == 919 || item.id == 918 || item.id == 917 || item.id == 916 || item.id == 910 || item.id == 909 || item.id == 908 || item.id == 893 || item. id == 892 ||
                        item.id == 1039 || item.id == 1040 || item.id == 1046 || item.id == 1107 || item.id == 1114 || item.id == 1147)
                {
//                    if(p.pet2Follow == (byte)0) {
//                        p.pet2Follow = (byte)1;
//                        p.itemPet2 = item;
//                        short headpet2 = Service.gI().getHeadPet2(item.id);
//                        p.pet = new Detu();
//                        p.pet.initPet(headpet2, (short)(headpet2 + 1), (short)(headpet2 + 2));
//                        Service.gI().LoadPetFollow(p);
//                        //load point moi cho ME
//                        Service.gI().loadPoint(p.session, p);
//                        //send uphp to all player khac trong map
//                        p.updateHpToPlayerInMap(p, p.hp);
//                    } else if(p.pet2Follow == (byte)1){
//                        p.pet2Follow = (byte)0;
//                        p.zone.leavePETTT(p.pet);
//                        p.itemPet2 = null;
//                        p.pet = null;
//                        //load point moi cho ME
//                        Service.gI().loadPoint(p.session, p);
//                        //send uphp to all player khac trong map
//                        p.updateHpToPlayerInMap(p, p.hp);
//                    }

                    if (p.ItemBody[7] != null) {
                        p.ItemBag[index] = p.ItemBody[7];
                    } else {
                        p.ItemBag[index] = null;
                    }
                    p.ItemBody[7] = item;
                    //NEU THAO CAI TRANG O MAP COOL
                    if(item.template.type == 5 && p.map.MapCold()) {
                        p.zone.upDownPointMapCool(p);
                    }

                    Service.gI().initPet2(p, item.id);
                    p.LOADCAITRANGTOME();
//                    short headpet2 = Service.gI().getHeadPet2(item.id);
//                    p.pet = new Detu();
//                    p.pet.initPet(headpet2, (short)(headpet2 + 1), (short)(headpet2 + 2));
//                    Service.gI().LoadPetFollow(p);
//                    //load point moi cho ME
//                    Service.gI().loadPoint(p.session, p);
//                    //send uphp to all player khac trong map
//                    p.updateHpToPlayerInMap(p, p.hp);
//
//                    p.sortBag();
//                    Service.gI().updateItemBag(p);
//                    Service.gI().updateItemBody(p);
                    return;
                }
                else if(item.id == 1100)
                {
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Service.gI().updateItemBag(p);

                    //RANDOM ITEM
                    int rdItem = Util.nextInt(0, 100);
                    Item itemBox = null;
                    Item _itemCT = null;
                    if(rdItem < 65) {
                        rdItem = Util.nextInt(380, 386);
                        itemBox = ItemSell.getItemNotSell(rdItem);
                        _itemCT = new Item(itemBox);
                    } else if(rdItem >= 65) {
                        rdItem = Util.nextInt(1048, 1055);
                        if(rdItem == 1051) {
                            rdItem = 1049;
                        }
                        itemBox = ItemSell.getItemNotSell(rdItem);
                        _itemCT = new Item(itemBox);
                        _itemCT.itemOptions.clear();
                        _itemCT.itemOptions.add(new ItemOption(50, Util.nextInt(1, 41)));
                        _itemCT.itemOptions.add(new ItemOption(77, Util.nextInt(1, 32)));
                        _itemCT.itemOptions.add(new ItemOption(103, Util.nextInt(1, 32)));
                        _itemCT.itemOptions.add(new ItemOption(14, Util.nextInt(1, 6)));
                        _itemCT.itemOptions.add(new ItemOption(94, Util.nextInt(1, 6)));
                        _itemCT.itemOptions.add(new ItemOption(108, Util.nextInt(1, 6)));
                        _itemCT.itemOptions.add(new ItemOption(30, 0)); //KHONG THE GIAO DICH
                        _itemCT.itemOptions.add(new ItemOption(106, 0)); //CHONG LANH
                        int day = Util.nextInt(3, 8);
                        _itemCT.itemOptions.add(new ItemOption(93, day));
                        _itemCT.timeHSD = System.currentTimeMillis() + (long)day*86400000;
                    }
                    if(_itemCT != null) {
                        if(p.addItemToBag(_itemCT)) {
                            p.sendAddchatYellow("Bạn vừa nhận được " + _itemCT.template.name);
                            Service.gI().updateItemBag(p);
                        }
                    }
                    return;
                } //NHAN THOI KHONG SAI LECH
                else if(item.id == 992)
                {
                    if(!p.isdie) {
//                    p.waitTransport = true;
                        Service.gI().dropDragonBall(p);
//                    Service.gI().transportTauNgam(p, (short)5, (byte)1);
//                    Service.gI().teleportByTauNgam(p, 160, (long)6000);
                        Service.gI().useRingBlack(p);
                    }
                    return;
                }
            }
            else if(item.template.type == (byte)6)
            {
                if(index != -1){
                    if(p.ItemBag[index].template.type == 6 && !p.isdie){ //type 6 la dau than
                        long _TIMENOW = System.currentTimeMillis();
                        if(p.ItemBag[index].quantity > 0 && ((_TIMENOW - p._TIMEBUFFDAU) > 10000)){
                            p._TIMEBUFFDAU = _TIMENOW;
                            int hpbuff = p.ItemBag[index].getParamItemByID(48);
                            if(hpbuff == 0 ) {
                                hpbuff = p.ItemBag[index].getParamItemByID(2)*1000;
                            }
                            p.hp = (p.hp + hpbuff) > p.getHpFull() ? p.getHpFull() : (p.hp + hpbuff);
                            p.mp = (p.mp + hpbuff) > p.getMpFull() ? p.getMpFull() : (p.mp + hpbuff);

                            if(p.havePet == 1 && !p.detu.isdie) {
                                p.detu.hp = (p.detu.hp + hpbuff) > p.detu.getHpFull() ? p.detu.getHpFull() : (p.detu.hp + hpbuff);
                                p.detu.mp = (p.detu.mp + hpbuff) > p.detu.getMpFull() ? p.detu.getMpFull() : (p.detu.mp + hpbuff);
                                p.detu.stamina = (short)(p.detu.stamina + (short)(p.ItemBag[index].template.level)*100) > (short)1000 ? (short)1000 : (short)(p.detu.stamina + (short)(p.ItemBag[index].template.level)*100);
                                p.updateHpDetu(p, p.detu);
                                if(p.petfucus == 1 && p.statusPet != 3 && p.statusPet != 4) {
                                    p.zone.chat(p.detu, "Con cám ơn sư phụ");
                                }
                            }
                            p.updateQuantityItemBag(index, 1);
                            Service.gI().loadPoint(p.session,p);
//                                    Service.gI().updateItemBag(p);
                        }
                    }
                }
                return;
            }
            else if(item.template.type == (byte)33)
            { //DUNG RADA CARD
                if(index != -1 && p.ItemBag[(byte)index] != null && p.ItemBag[(byte)index].quantity >= 1 && 
                ((p.ItemBag[(byte)index].id >= 828 && p.ItemBag[(byte)index].id <= 842) || p.ItemBag[(byte)index].id == 859 || p.ItemBag[(byte)index].id == 956)){
                    short idCard = (short)(p.ItemBag[(byte)index].id);
                    p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity <= 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Service.gI().updateItemBag(p);
                    RadaCardService.gI().setAmountCard(p, idCard);
                } else {
                    p.sendAddchatYellow("Không phải thẻ sưu tầm");
                }
                return;
            }

            if(item.id == 454 || item.id == 921)
            {
                Message m = null;
                if(p.power >= 1500000) {
                    if((p.map.id != 21 && p.map.id != 22 && p.map.id != 23) && (!p.isdie && p.detu != null && !p.detu.isdie && p.havePet == 1)) {
                        if((System.currentTimeMillis() - p.tFusion) > 0) {
                            p.tFusion = System.currentTimeMillis() + 10000;
                            if(item.id == 921) {
                                p.isPorata2 = true;
                            } else {
                                p.isPorata2 = false;
                            }
                            if(p.NhapThe == 0){
                                p.NhapThe = 1;
                                p.detu.isMonkey = false;
                                p.detu.hp = p.detu.hp > p.detu.getHpFull() ? p.detu.getHpFull() : p.detu.hp;
        //                        Service.gI().loadPlayer(p.session, p);
                                if(!p.isMonkey) {
                                    Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartHead() + 1, p.PartHead() + 2);
                                }
                                //HOP THE THI HOI FULL HP, KI
                                p.hp = p.getHpFull();
                                p.mp = p.getMpFull();
                                //send effect hop the
                                try {
                                    m = new Message(125);
                                    m.writer().writeByte(6); //type hop the 6 la bongtai, 1 la huy hop the
                                    m.writer().writeInt(p.id); // id player use
                                    m.writer().flush();
                                    for(byte i = 0; i < p.zone.players.size(); i++) {
                                        if(p.zone.players.get(i) != null) {
                                            p.zone.players.get(i).session.sendMessage(m);
                                        }
                                    }
//                                    for(Player pl: p.zone.players) {
//                                        pl.session.sendMessage(m);
//                                    }
                                    m.cleanup();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(m != null) {
                                        m.cleanup();
                                    }
                                }

                                p.statusPet = 3; //change status de tu thanh ve nha
                                p.petfucus = 0;
                                // remove detu
                                p.zone.leaveDEEEEE(p.detu);
                                //load point moi cho ME
                                Service.gI().loadPoint(p.session, p);
                                //send uphp to all player khac trong map
                                p.updateHpToPlayerInMap(p, p.getHpFull());
                            }
                            else{
                                p.NhapThe = 0;
                                p.isPorata2 = false;
        //                        Service.gI().loadPlayer(p.session, p);  
                                if(!p.isMonkey) {
                                    if(p.ItemBody[5] != null && p.ItemBody[5].template.level != 0 && (p.ItemBody[5].template.id != 601 && p.ItemBody[5].template.id != 602 && 
            p.ItemBody[5].template.id != 603 && p.ItemBody[5].template.id != 639 && p.ItemBody[5].template.id != 640 && p.ItemBody[5].template.id != 641)){
                                        if(p.ItemBody[5] != null && p.ItemBody[5].template.id == 604) {//CAI TRANG VIP
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 472, 473);
                                        } else if(p.ItemBody[5] != null && p.ItemBody[5].template.id == 605) {//CAI TRANG VIP
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 476, 477);
                                        } else if(p.ItemBody[5] != null && p.ItemBody[5].template.id == 606) {//CAI TRANG VIP
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 474, 475);
                                        } else if(p.ItemBody[5].template.id >= 592 && p.ItemBody[5].template.id <= 594) { //YADART
                                            if(p.id == 1) {
                                                Service.gI().LoadBodyPlayerChange(p, 1, 126, 523, 524);
                                            } else {
                                                Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 523, 524);
                                            }
                                        } else if(p.ItemBody[5] != null && (p.ItemBody[5].template.part != (short)(-1) || p.ItemBody[5].template.id == 545 || p.ItemBody[5].template.id == 546 || 
                                        p.ItemBody[5].template.id == 857 || p.ItemBody[5].template.id == 858)) {
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartBody(), p.Leg());
                                        } else {
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartHead() + 1, p.PartHead() + 2);
                                        }
                                    }
                                    else{
                                        Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartBody(), p.Leg());
                                    }
                                }
                                try {
                                    m = new Message(125);
                                    m.writer().writeByte(1); //type hop the 6 la bongtai, 1 la huy hop the
                                    m.writer().writeInt(p.id); // id player use
                                    m.writer().flush();
                                    for(byte i = 0; i < p.zone.players.size(); i++) {
                                        if(p.zone.players.get(i) != null) {
                                            p.zone.players.get(i).session.sendMessage(m);
                                        }
                                    }
//                                    for(Player pl: p.zone.players) {
//                                        pl.session.sendMessage(m);
//                                    }
                                    m.cleanup();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(m != null) {
                                        m.cleanup();
                                    }
                                }
                                p.statusPet = 0; //change status de tu thanh di theo
                                p.petfucus = 1;
                                Service.gI().LoadDeTuFollow(p, 1);
                                //load point moi cho ME
                                Service.gI().loadPoint(p.session, p);
                                //send uphp to all player khac trong map
                                p.updateHpToPlayerInMap(p, p.getHpFull());
                            };
                        } else {
                            p.sendAddchatYellow("Vui lòng chờ " + (int)((p.tFusion - System.currentTimeMillis())/1000) + " giây nữa");
                        }
                    } else {
                        p.sendAddchatYellow("Không thể thực hiện");
                    }
                } else {
                    p.sendAddchatYellow("Cần 1tr5 sức mạnh mới có thể sử dụng!");
                }
                return;
            }
            
            switch (item.id) {
                case 454: //bongtai porata
                    Message m = null;
                    if(p.power >= 1500000) {
                        if(!p.isdie && p.detu != null && !p.detu.isdie && p.havePet == 1) {
                            if(p.NhapThe == 0){
                                p.NhapThe = 1;
                                p.detu.isMonkey = false;
                                p.detu.hp = p.detu.hp > p.detu.getHpFull() ? p.detu.getHpFull() : p.detu.hp;
        //                        Service.gI().loadPlayer(p.session, p);
                                if(!p.isMonkey) {
                                    Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartHead() + 1, p.PartHead() + 2);
                                }
                                //HOP THE THI HOI FULL HP, KI
                                p.hp = p.getHpFull();
                                p.mp = p.getMpFull();
                                //send effect hop the
                                try {
                                    m = new Message(125);
                                    m.writer().writeByte(6); //type hop the 6 la bongtai, 1 la huy hop the
                                    m.writer().writeInt(p.id); // id player use
                                    m.writer().flush();
                                    for(byte i = 0; i < p.zone.players.size(); i++) {
                                        if(p.zone.players.get(i) != null) {
                                            p.zone.players.get(i).session.sendMessage(m);
                                        }
                                    }
//                                    for(Player pl: p.zone.players) {
//                                        pl.session.sendMessage(m);
//                                    }
                                    m.cleanup();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(m != null) {
                                        m.cleanup();
                                    }
                                }

                                p.statusPet = 3; //change status de tu thanh ve nha
                                p.petfucus = 0;
                                // remove detu
                                p.zone.leaveDEEEEE(p.detu);
                                //load point moi cho ME
                                Service.gI().loadPoint(p.session, p);
                                //send uphp to all player khac trong map
                                p.updateHpToPlayerInMap(p, p.getHpFull());
                            }
                            else{
                                p.NhapThe = 0;   
        //                        Service.gI().loadPlayer(p.session, p);  
                                if(!p.isMonkey) {
                                    if(p.ItemBody[5] != null && p.ItemBody[5].template.level != 0 && (p.ItemBody[5].template.id != 601 && p.ItemBody[5].template.id != 602 && 
            p.ItemBody[5].template.id != 603 && p.ItemBody[5].template.id != 639 && p.ItemBody[5].template.id != 640 && p.ItemBody[5].template.id != 641)){
                                        if(p.ItemBody[5] != null && p.ItemBody[5].template.id == 604) {//CAI TRANG VIP
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 472, 473);
                                        } else if(p.ItemBody[5] != null && p.ItemBody[5].template.id == 605) {//CAI TRANG VIP
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 476, 477);
                                        } else if(p.ItemBody[5] != null && p.ItemBody[5].template.id == 606) {//CAI TRANG VIP
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 474, 475);
                                        } else if(p.ItemBody[5].template.id >= 592 && p.ItemBody[5].template.id <= 594) { //YADART
                                            if(p.id == 1) {
                                                Service.gI().LoadBodyPlayerChange(p, 1, 126, 523, 524);
                                            } else {
                                                Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), 523, 524);
                                            }
                                        } else if(p.ItemBody[5] != null && (p.ItemBody[5].template.part != (short)(-1) || p.ItemBody[5].template.id == 545 || p.ItemBody[5].template.id == 546 ||
p.ItemBody[5].template.id == 857 || p.ItemBody[5].template.id == 858)) {
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartBody(), p.Leg());
                                        } else {
                                            Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartHead() + 1, p.PartHead() + 2);
                                        }
                                    }
                                    else{
                                        Service.gI().LoadBodyPlayerChange(p, 1, p.PartHead(), p.PartBody(), p.Leg());
                                    }
                                }
                                try {
                                    m = new Message(125);
                                    m.writer().writeByte(1); //type hop the 6 la bongtai, 1 la huy hop the
                                    m.writer().writeInt(p.id); // id player use
                                    m.writer().flush();
                                    for(byte i = 0; i < p.zone.players.size(); i++) {
                                        if(p.zone.players.get(i) != null) {
                                            p.zone.players.get(i).session.sendMessage(m);
                                        }
                                    }
//                                    for(Player pl: p.zone.players) {
//                                        pl.session.sendMessage(m);
//                                    }
                                    m.cleanup();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(m != null) {
                                        m.cleanup();
                                    }
                                }
                                p.statusPet = 0; //change status de tu thanh di theo
                                p.petfucus = 1;
                                Service.gI().LoadDeTuFollow(p, 1);
                                //load point moi cho ME
                                Service.gI().loadPoint(p.session, p);
                                //send uphp to all player khac trong map
                                p.updateHpToPlayerInMap(p, p.getHpFull());
                            };
                        } else {
                            p.sendAddchatYellow("Không thể sử dụng");
                        }
                    } else {
                        p.sendAddchatYellow("Cần 1tr5 sức mạnh mới có thể sử dụng!");
                    }
                    break;
                
case 568:{
  p.ItemBag[(byte)index].quantity -= 1;
                    if(p.ItemBag[(byte)index].quantity == 0) {
                        p.ItemBag[(byte)index] = null;
                    }
                    Service.gI().updateItemBag(p);
                    p.hasTrungMabu = true;
                                                p.sendAddchatYellow("Bạn vừa nhận được đệ tử Mabư, quay về nhà gặp Ông Già để thao tác");
}
                case 193:
                    p.indexItemUse = index;
                    Service.gI().openUITransport(p);
                    break;
                case 194:
                    Service.gI().openUITransport(p);
                    break;
                default: {
                    p.sendAddchatYellow("Chức năng đang được cập nhật");
                    break;
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //set setup fusion
    public void sendFusion(Player _player) {
        Message m = null;
        //
        try {
            m = new Message(125);
            m.writer().writeByte(6); //type hop the
            m.writer().writeInt(_player.id); // id player use
            m.writer().flush();
            for(byte i = 0; i < _player.zone.players.size(); i++) {
                if(_player.zone.players.get(i) != null) {
                    _player.zone.players.get(i).session.sendMessage(m);
                }
            }
//            for(Player p: _player.zone.players) {
//                p.session.sendMessage(m);
//            }
            m.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }
    }
}
