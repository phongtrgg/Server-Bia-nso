package com.hoiuc.server;

import com.hoiuc.assembly.*;
import com.hoiuc.io.Message;
import com.hoiuc.io.SQLManager;
import com.hoiuc.io.Util;
import com.hoiuc.stream.*;
import com.hoiuc.stream.thiendiabang.ThienDiaData;
import com.hoiuc.template.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hoiuc.assembly.CheckCLCoin;
import com.hoiuc.assembly.CheckCLLuong;
import com.hoiuc.assembly.CheckTXCoin;
import com.hoiuc.assembly.CheckTXLuong;
import com.hoiuc.assembly.CheckTXXu;
import java.util.Calendar;

public class Menu {
    public void sendMessMenuNhiemVu(Player p, byte npcid, byte menuId, String str) throws IOException {
        NpcTemplate npc = (NpcTemplate)Manager.npcs.get(npcid);
        Message mss = new Message(39);
        DataOutputStream ds = mss.writer();
        ds.writeShort(npcid);
        ds.writeUTF(str);
        ds.writeByte(npc.menu[menuId].length);

        for(int i = 1; i < npc.menu[menuId].length; ++i) {
            ds.writeUTF(npc.menu[menuId][i]);
        }

        ds.flush();
        p.conn.sendMessage(mss);
        mss.cleanup();
    }
    
    public void test(){
        System.out.println("a");
    }

    public static void doMenuArray(Player p, String[] menu) {
        Message m = null;
        try {
            m = new Message(63);
            for(byte i = 0; i < menu.length; ++i) {
                m.writer().writeUTF(menu[i]);
            }
            m.writer().flush();
            p.conn.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }

    public static void sendWrite(Player p, short type, String title) {
        Message m = null;
        try {
            m = new Message(92);
            m.writer().writeUTF(title);
            m.writer().writeShort(type);
            m.writer().flush();
            p.conn.sendMessage(m);
            m.cleanup();
        } catch (IOException var5) {
            var5.printStackTrace();
        } finally {
            if(m != null) {
                m.cleanup();
            }
        }

    }

    public static void menuId(Player p, Message ms) {
        try {
            short npcId = ms.reader().readShort();
            ms.cleanup();
            p.c.typemenu = 0;
            p.typemenu = npcId;
            if (npcId == 33) {
                switch(Server.manager.event) {
                    case 1: {
                        Menu.doMenuArray(p, new String[]{"Diều giấy", "Diều vải"});
                        break;
                    }
                    case 2: {
                        Menu.doMenuArray(p, new String[]{"Hộp bánh thường", "Hộp bánh thượng hạng", "Bánh thập cẩm", "Bánh dẻo", "Bánh đậu xanh", "Bánh pía"});
                        break;
                    }
                    case 3: {
                        Menu.doMenuArray(p, new String[]{"Bánh Chocolate", "Bánh dâu tây", "Đổi mặt nạ", "Đổi pet","BXH Diệt Boss TL", "Hướng dẫn"});
                        break;
                    }
                    case 4: {
                        Menu.doMenuArray(p, new String[]{"Bánh Chưng","Bánh Tét","Lì xì","Làm Pháo","Top diệt chuột", "Hướng dẫn"});
                        break;
                    }
                    case 5: {
                        Menu.doMenuArray(p, new String[]{"Làm Hoa Hồng Đỏ","Làm Hoa Hồng Vàng","Làm Hoa Hồng Xanh","Làm Giỏ Hoa","Tặng Hoa Hồng Đỏ","Tặng Hoa Hồng Vàng","Tặng Hoa Hồng Xanh","Tặng Giỏ Hoa","Kết Hoa"});
                    }
                    default: {
                        break;
                    }
                }
            }
            else if(npcId == 40) {
                switch (p.c.mapid) {
                    case 117: {
                        Menu.doMenuArray(p, new String[]{"Rời khỏi nơi này", "Đặt cược", "Hướng dẫn"});
                        break;
                    }
                    case 118:
                    case 119: {
                        Menu.doMenuArray(p, new String[]{"Rời khỏi nơi này", "Thông tin"});
                        break;
                    }
                }
            }

             ms = new Message((byte)40);
            ms.writer().flush();
            p.conn.sendMessage(ms);
            ms.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ms != null) {
                ms.cleanup();
            }
        }
    }

    public static void menu(Player p, Message ms) {
        try {
            byte npcId = ms.reader().readByte();
            byte menuId = ms.reader().readByte();
            byte b3 = ms.reader().readByte();
            if (ms.reader().available() > 0) {
                byte var6 = ms.reader().readByte();
            }
            ms.cleanup();
            if ((p.typemenu == -1 || p.typemenu == 0) && p.typemenu != npcId) {
                switch(npcId) {
                    case 0:
                        Menu.npcKanata(p, npcId, menuId, b3);
                        break;
                    case 1:
                        Menu.npcFuroya(p, npcId, menuId, b3);
                        break;
                    case 2:
                        Menu.npcAmeji(p, npcId, menuId, b3);
                        break;
                    case 3:
                        Menu.npcKiriko(p, npcId, menuId, b3);
                        break;
                    case 4:
                        Menu.npcTabemono(p, npcId, menuId, b3);
                        break;
                    case 5:
                        Menu.npcKamakura(p, npcId, menuId, b3);
                        break;
                    case 6:
                        Menu.npcKenshinto(p, npcId, menuId, b3);
                        break;
                    case 7:
                        Menu.npcUmayaki_Lang(p, npcId, menuId, b3);
                        break;
                    case 8:
                        Menu.npcUmayaki_Truong(p, npcId, menuId, b3);
                        break;
                    case 9:
                        Menu.npcToyotomi(p, npcId, menuId, b3);
                        break;
                    case 10:
                        Menu.npcOokamesama(p, npcId, menuId, b3);
                        break;
                    case 11:
                        Menu.npcKazeto(p, npcId, menuId, b3);
                        break;
                    case 12:
                        Menu.npcTajima(p, npcId, menuId, b3);
                        break;
                    case 18:
                        Menu.npcRei(p, npcId, menuId, b3);
                        break;
                    case 19:
                        Menu.npcKirin(p, npcId, menuId, b3);
                        break;
                    case 20:
                        Menu.npcSoba(p, npcId, menuId, b3);
                        break;
                    case 21:
                        Menu.npcSunoo(p, npcId, menuId, b3);
                        break;
                    case 22:
                        Menu.npcGuriin(p, npcId, menuId, b3);
                        break;
                    case 23:
                        Menu.npcMatsurugi(p, npcId, menuId, b3);
                        break;
                    case 24:
                        Menu.npcOkanechan(p, npcId, menuId, b3);
                        break;
                    case 25:
                        Menu.npcRikudou(p, npcId, menuId, b3);
                        break;
                    case 26:
                        Menu.npcGoosho(p, npcId, menuId, b3);
                        break;
                    case 27:
                        Menu.npcTruCoQuan(p, npcId, menuId, b3);
                        break;
                    case 28:
                        Menu.npcShinwa(p, npcId, menuId, b3);
                        break;
                    case 29:
                        Menu.npcChiHang(p, npcId, menuId, b3);
                        break;
                    case 30:
                        Menu.npcRakkii(p, npcId, menuId, b3);
                        break;
                    case 31:
                        Menu.npcLongDen(p, npcId, menuId, b3);
                        break;
                    case 32:
                        Menu.npcKagai(p, npcId, menuId, b3);
                        break;
                    case 33:
                        Menu.npcTienNu(p, npcId, menuId, b3);
                        break;
                    case 34:
                        Menu.npcCayThong(p, npcId, menuId, b3);
                        break;
                    case 35:
                        Menu.npcOngGiaNoen(p, npcId, menuId, b3);
                        break;
                    case 36:
                        Menu.npcVuaHung(p, npcId, menuId, b3);
                        break;
                    case 37:
                        Menu.npcKanata_LoiDai(p, npcId, menuId, b3);
                        break;                       
                    case 38:
                        Menu.npcAdmin(p, npcId, menuId, b3);
                        break;
                    case 39: {
                        Menu.npcRikudou_ChienTruong(p, npcId, menuId, b3);
                        break;
                    }
                     
                    case 40: {
                        Menu.npcKagai_GTC(p, npcId, menuId, b3);
                        break;
                    }
                     case 41:{ 
                        Menu.npccaydau(p, npcId, menuId, b3);
                        break;
                    }
                     case 42:{ 
                        Menu.npccasino(p, npcId, menuId, b3);
                        break;
                    }
                    case 43:{ 
                        Menu.npcHoadao(p, npcId, menuId, b3);
                        break;
                    }
                    case 44: {
                        Menu.npcVip(p, npcId, menuId, b3);
                        break;
                    }
                    case 45: {
                        Menu.npcKheUoc(p, npcId, menuId, b3);
                        break;
                    }
                    case 46: {
                        Menu.npcCLXTCoin(p, npcId, menuId, b3);
                        break;
                    }
                    case 47: {
                        Menu.npcSuKien(p, npcId, menuId, b3);
                        break;
                    }
                    case 48: {
                        Menu.npcPhoBan(p, npcId, menuId, b3);
                        break;
                    }
                     case 49: {
                        Menu.npcquylao(p, npcId, menuId, b3);
                        break;
                    }
                    
                    case 50: {
                        Menu.npcthiensuwhis(p, npcId, menuId, b3);
                        break;
                    }
                     case 51: {
                        Menu.npcquocvuong(p, npcId, menuId, b3);
                        break;
                    }
                      case 52: {
                        Menu.npcCLXTXu(p, npcId, menuId, b3);
                        break;
                    }
                    
                    case 53: {
                        Menu.npcCLXTLuong(p, npcId, menuId, b3);
                        break;
                    }
                    case 54: {
                        Menu.npcThanMeo(p, npcId, menuId, b3);
                        break;
                    }
                    case 55: {
                        Menu.npcBulma(p, npcId, menuId, b3);
                        break;
                    }
                     case 56: {
                        Menu.BXH(p, npcId, menuId, b3);
                        break;
                    }
                   case 57: {
                        Menu.luyenma(p, npcId, menuId, b3);
                        break;
                    }
                   case 58: {
                        Menu.npcott(p, npcId, menuId, b3);
                        break;
                    }
                   case 59: {
                        Menu.doimk(p, npcId, menuId, b3);
                        break;
                    }
                   case 60: {
                        Menu.menuDoiVK(p, npcId, menuId, b3);
                        break;
                    }
                   case 61: {
                        Menu.npchoncot(p, npcId, menuId, b3);
                        break;
                    }
                   case 62: {
                        Menu.npctinhfox(p, npcId, menuId, b3);
                        break;
                    }
                   case 63: {
                        Menu.npcbaove(p, npcId, menuId, b3);
                        break;
                    }
                   case 64: {
                        Menu.npcnhanhoncot(p, npcId, menuId, b3);
                        break;
                    }
                   case 65: {
                        Menu.npcmathan(p, npcId, menuId, b3);
                        break;
                   }
                   case 66: {
                        Menu.npcNuoiRong(p, npcId, menuId, b3);
                        break;
                   }
                 case 67: {
                        Menu.npcnhucthan(p, npcId, menuId, b3);
                        break;
                 }
                      case 68: {
                        Menu.npcnhucthan1(p, npcId, menuId, b3);
                        break;
                   }  
                      case 69: {
                        Menu.npcphuhao(p, npcId, menuId, b3);
                        break;
                   }  
                      case 70: {
                        Menu.npctaphoa(p, npcId, menuId, b3);
                        break;
                   }  
                      case 71: {
                        Menu.npcTaiXiu(p, npcId, menuId, b3);
                        break;
                   }
                      case 72: {
                        Menu.npcvkthan(p, npcId, menuId, b3);
                        break;
                   }
                       case 73: {
                        Menu.npcdichchuyen(p, npcId, menuId, b3);
                        break;
                   }
                      case 74: {
                        Menu.npcuocngocrong(p, npcId, menuId, b3);
                        break;
                   }
                      case 75: {
                        Menu.npcSanBoss(p, npcId, menuId, b3);
                        break;
                   }
                      case 76: {
                        Menu.npcDiHoc(p, npcId, menuId, b3);
                        break;
                   }
                      case 77: {
                        Menu.npcCayHoaDen(p, npcId, menuId, b3);
                        break;
                   }
                      case 78: {
                        Menu.npcCayDen(p, npcId, menuId, b3);
                        break;
                   }
                      case 79: {
                        Menu.npcBossCui(p, npcId, menuId, b3);
                        break;
                   }
                    case 92:
                        p.typemenu = menuId == 0 ? 93 : 94;
                        Menu.doMenuArray(p, new String[]{"Thông tin", "Luật chơi"});
                        break;
                    case 93:
                        if (menuId == 0) {
                            Server.manager.rotationluck[0].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "Vòng xoay vip", "Tham gia đi, xem luật làm gì");
                        }
                        break;
                    case 94:
                        if (menuId == 0) {
                            Server.manager.rotationluck[1].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "Vòng xoay thường", "Tham gia đi xem luật lm gì");
                        }
                    case 95:
                        break;
                    case 120: {
                        if (menuId > 0 && menuId < 7) {
                            Admission.Admission(p,menuId);
                        }
                    }
                    default: {
                        Service.chatNPC(p, (short) npcId, "Chức năng này đang được cập nhật");
                        break;
                    }
                }
            }
            else if (p.typemenu == npcId) {
                switch(p.typemenu) {
                    case 0:
                        Menu.npcKanata(p, npcId, menuId, b3);
                        break;
                    case 1:
                        Menu.npcFuroya(p, npcId, menuId, b3);
                        break;
                    case 2:
                        Menu.npcAmeji(p, npcId, menuId, b3);
                        break;
                    case 3:
                        Menu.npcKiriko(p, npcId, menuId, b3);
                        break;
                    case 4:
                        Menu.npcTabemono(p, npcId, menuId, b3);
                        break;
                    case 5:
                        Menu.npcKamakura(p, npcId, menuId, b3);
                        break;
                    case 6:
                        Menu.npcKenshinto(p, npcId, menuId, b3);
                        break;
                    case 7:
                        Menu.npcUmayaki_Lang(p, npcId, menuId, b3);
                        break;
                    case 8:
                        Menu.npcUmayaki_Truong(p, npcId, menuId, b3);
                        break;
                    case 9:
                        Menu.npcToyotomi(p, npcId, menuId, b3);
                        break;
                    case 10:
                        Menu.npcOokamesama(p, npcId, menuId, b3);
                        break;
                    case 11:
                        Menu.npcKazeto(p, npcId, menuId, b3);
                        break;
                    case 12:
                        Menu.npcTajima(p, npcId, menuId, b3);
                        break;
                    case 18:
                        Menu.npcRei(p, npcId, menuId, b3);
                        break;
                    case 19:
                        Menu.npcKirin(p, npcId, menuId, b3);
                        break;
                    case 20:
                        Menu.npcSoba(p, npcId, menuId, b3);
                        break;
                    case 21:
                        Menu.npcSunoo(p, npcId, menuId, b3);
                        break;
                    case 22:
                        Menu.npcGuriin(p, npcId, menuId, b3);
                        break;
                    case 23:
                        Menu.npcMatsurugi(p, npcId, menuId, b3);
                        break;
                    case 24:
                        Menu.npcOkanechan(p, npcId, menuId, b3);
                        break;
                    case 25:
                        Menu.npcRikudou(p, npcId, menuId, b3);
                        break;
                    case 26:
                        Menu.npcGoosho(p, npcId, menuId, b3);
                        break;
                    case 27:
                        Menu.npcTruCoQuan(p, npcId, menuId, b3);
                        break;
                    case 28:
                        Menu.npcShinwa(p, npcId, menuId, b3);
                        break;
                    case 29:
                        Menu.npcChiHang(p, npcId, menuId, b3);
                        break;
                    case 30:
                        Menu.npcRakkii(p, npcId, menuId, b3);
                        break;
                    case 31:
                        Menu.npcLongDen(p, npcId, menuId, b3);
                        break;
                    case 32:
                        Menu.npcKagai(p, npcId, menuId, b3);
                        break;
                    case 33:
                        Menu.npcTienNu(p, npcId, menuId, b3);
                        break;
                    case 34:
                        Menu.npcCayThong(p, npcId, menuId, b3);
                        break;
                    case 35:
                        Menu.npcOngGiaNoen(p, npcId, menuId, b3);
                        break;
                    case 36:
                        Menu.npcVuaHung(p, npcId, menuId, b3);
                        break;                  
                    case 37:
                        Menu.npcKanata_LoiDai(p, npcId, menuId, b3);
                        break;
                    case 38:
                        Menu.npcAdmin(p, npcId, menuId, b3);
                        break;
                    case 39: {
                        Menu.npcRikudou_ChienTruong(p, npcId, menuId, b3);
                        break;
                    }
                     case 40: {
                        Menu.npcKagai_GTC(p, npcId, menuId, b3);
                        break;
                    }
                     case 41:{ 
                        Menu.npccaydau(p, npcId, menuId, b3);
                        break;
                    }
                     case 42:{ 
                        Menu.npccasino(p, npcId, menuId, b3);
                        break;
                    }
                    case 43:{ 
                        Menu.npcHoadao(p, npcId, menuId, b3);
                        break;
                    }
                    case 44: {
                        Menu.npcVip(p, npcId, menuId, b3);
                        break;
                    }
                    case 45: {
                        Menu.npcKheUoc(p, npcId, menuId, b3);
                        break;
                    }
                    case 46: {
                        Menu.npcCLXTCoin(p, npcId, menuId, b3);
                        break;
                    }
                    case 47: {
                        Menu.npcSuKien(p, npcId, menuId, b3);
                        break;
                    }
                    case 48: {
                        Menu.npcPhoBan(p, npcId, menuId, b3);
                        break;
                    }  case 49: {
                        Menu.npcquylao(p, npcId, menuId, b3);
                        break;
                    }
                    case 50: {
                        Menu.npcthiensuwhis(p, npcId, menuId, b3);
                        break;
                    }
                     case 51: {
                        Menu.npcquocvuong(p, npcId, menuId, b3);
                        break;
                    }
                      case 52: {
                        Menu.npcCLXTXu(p, npcId, menuId, b3);
                        break;
                    }
                    
                    case 53: {
                        Menu.npcCLXTLuong(p, npcId, menuId, b3);
                        break;
                    }
                    case 54: {
                        Menu.npcThanMeo(p, npcId, menuId, b3);
                        break;
                    }
                    case 55: {
                        Menu.npcBulma(p, npcId, menuId, b3);
                        break;
                    }
                    case 56: {
                        Menu.BXH(p, npcId, menuId, b3);
                        break;
                    }
                    case 57: {
                        Menu.luyenma(p, npcId, menuId, b3);
                        break;
                    }
                    case 58: {
                        Menu.npcott(p, npcId, menuId, b3);
                        break;
                    }
                   case 59: {
                        Menu.doimk(p, npcId, menuId, b3);
                        break;
                    }
                    case 60: {
                        Menu.menuDoiVK(p, npcId, menuId, b3);
                        break;
                    }
                    case 61: {
                        Menu.npchoncot(p, npcId, menuId, b3);
                        break;
                    }
                   case 62: {
                        Menu.npctinhfox(p, npcId, menuId, b3);
                        break;
                    }
                   case 63: {
                        Menu.npcbaove(p, npcId, menuId, b3);
                        break;
                    }
                   case 64: {
                        Menu.npcnhanhoncot(p, npcId, menuId, b3);
                        break;
                    }
                   case 65: {
                        Menu.npcmathan(p, npcId, menuId, b3);
                        break;
                   }
                   case 66: {
                        Menu.npcNuoiRong(p, npcId, menuId, b3);
                        break;
                   }
                   case 67: {
                        Menu.npcnhucthan(p, npcId, menuId, b3);
                        break;
                 }
                      case 68: {
                        Menu.npcnhucthan1(p, npcId, menuId, b3);
                        break;
                   }  
                      case 69: {
                        Menu.npcphuhao(p, npcId, menuId, b3);
                        break;
                   }  
                      case 70: {
                        Menu.npctaphoa(p, npcId, menuId, b3);
                        break;
                   }  
                      case 71: {
                        Menu.npcTaiXiu(p, npcId, menuId, b3);
                        break;
                   }
                      case 72: {
                        Menu.npcvkthan(p, npcId, menuId, b3);
                        break;
                   }
                      case 73: {
                        Menu.npcdichchuyen(p, npcId, menuId, b3);
                        break;
                   }
                      case 74: {
                        Menu.npcuocngocrong(p, npcId, menuId, b3);
                        break;
                   }
                     case 75: {
                        Menu.npcSanBoss(p, npcId, menuId, b3);
                        break;
                   }
                    case 76: {
                        Menu.npcDiHoc(p, npcId, menuId, b3);
                        break;
                   }
                    case 77: {
                        Menu.npcCayHoaDen(p, npcId, menuId, b3);
                        break;
                   }
                    case 78: {
                        Menu.npcCayDen(p, npcId, menuId, b3);
                        break;
                   }
                    case 79: {
                        Menu.npcBossCui(p, npcId, menuId, b3);
                        break;
                   }
                    case 92:
                        p.typemenu = menuId == 0 ? 93 : 94;
                        doMenuArray(p, new String[]{"Thông tin", "Luật chơi"});
                        break;
                    case 93:
                        if (menuId == 0) {

                            Server.manager.rotationluck[0].luckMessage(p);
                        } else if (menuId == 1) {

                            Server.manager.sendTB(p, "Vòng xoay vip", "Tham gia đi, xem luật làm gì");
                        }
                        break;
                    case 94:
                        if (menuId == 0) {
                            Server.manager.rotationluck[1].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "Vòng xoay thường", "Tham gia đi xem luật lm gì");
                        }
                    case 95:
                        break;
                    case 120: {
                        if (menuId > 0 && menuId < 7) {
                            Admission.Admission(p,(byte)menuId);
                        }
                    }
                    default: {
                        Service.chatNPC(p, (short) npcId, "Chức năng này đang được cập nhật");
                        break;
                    }
                }
            }
            else {
                switch(p.typemenu) {
                    case -125: {
                        Menu.doiGiayVun(p, npcId, menuId, b3);
                        break;
                    }
                    case 92: {
                        switch (menuId) {
                            case 0: {
                                Server.manager.rotationluck[0].luckMessage(p);
                                break;
                            }
                            case 1: {
                                Server.manager.rotationluck[1].luckMessage(p);
                                break;
                            }
                        }
                        break;
                    }
                    //Mảnh top vk    
                    case 839:{
                        Menu.menuDoiVK(p, npcId, menuId, b3);
                        break;
                    }
                    
                    default: {
                        break;
                    }
                }
            }
            p.typemenu = 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ms != null) {
                ms.cleanup();
            }
        }
    }
    
    public static void npcHoadao(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId){
            case 0: {
                if (p.c.quantityItemyTotal(646) < 1){
                        Service.chatNPC(p, (short) npcid, "Con không có Bùa May mắn để Xin Lộc nhé");
                        return;
                       }
                else{
                    if(p.c.getBagNull() == 0){
                           p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                           return;
                }   short[] arId = new short[]{12,12,12,8,9,8,9,275,276,277,278,275,276,277,278,548,12,548,381,382,381,382,381,682,682,682,228,227,226,225,224,223,222,283,436,438,437,436,437,419,403,419,403,407,407,12,254,255,256,12,254,255,256,7,8,9,436,437,438,682,383,382,381,222,223,224,225,226,227,228,251, 308,309,548,275,276,277,278,539,540,674,695,696,697,698,699,674,700,701,702,703,704,733,734,735,736,737,738,739,674,740,741,760,761,762,674,763,764,765,766,767,768,674,695,696,697,698,699,674,700,701,702,703,704,733,734,735,736,737,738,739,674,740,741,760,761,762,674,763,764,765,766,767,768,674,695,696,697,698,699,674,700,701,702,703,704,733,734,735,736,737,738,739,674,740,741,760,761,762,674,763,764,765,766,767,768,289,290,291,289,290,291,289,290,291};
                        short idI = arId[Util.nextInt(arId.length)];
                        Item itemup = ItemTemplate.itemDefault(idI);
                       itemup.isLock = false;
                       itemup.isExpires = true;
                       itemup.expires = Util.TimeDay(7);
                        p.c.removeItemBags(646, 1);
                       p.c.addItemBag(false, itemup);
                       p.sendAddchatYellow("Bạn nhận được " + itemup);
                         p.updateExp(10000000L);
                }
                 break;
            }
            
            case 1: {
                Server.manager.sendTB(p,  "Hướng Dẫn", "Bạn cần 1 Bùa May Mắn để Xin Lộc Đầu Xuân và sẽ nhận được EXP và những món quà bất ngờ.");
                break;
            }              
        }       
    }

    public static void npcBulma(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                 if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                 if(p.c.level < 60) {
                    Service.chatNPC(p, (short) npcid, "Anh chưa đủ cấp 60 để tặng hoa cho iem\n Anh hãy tu luyện thêm rồi đến gặp iem!\n Yêu........");   
                    break;
                }
                 if(p.c.quantityItemyTotal(862) < 1) {
                    Service.chatNPC(p, (short) npcid, "Anh không có hoa để tặng tặng hoa cho iem rồi\n Dỗi........");   
                    return;
                 }
                 if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống để nhận quà");
                    return;
                }
                p.c.diemtanghoa += 1;
                p.c.removeItemBag(p.c.getIndexBagid(862, false), 1);
                Item it;
                int per = Util.nextInt(300);
                if(per<1) {
                    it = ItemTemplate.itemDefault(862);
                } else {
                    per = Util.nextInt(UseItem.idTangHoa.length);
                    it = ItemTemplate.itemDefault(UseItem.idTangHoa[per]);
                }
                p.c.removeItemBag(p.c.getIndexBagid(862, true), 1);
                it.isLock = false;
                it.quantity = 1;
                p.updateExp(10000000L);
                p.c.addItemBag(true, it);
                Service.chatNPC(p, (short) npcid, "Cảm ơn Anhh...Yêu"); 
                break;
        }
            case 1:{
              switch (b3) {
                    case 0:  {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemtanghoa < 5000) {
                    Service.chatNPC(p, (short) npcid, "Anh chưa đủ 5000 điểm tặng hoa để nhận đồ xịn\n Hãy tặng em đủ 5000 bó hoa hồng\n Yêu");   
                    break;
                }
                if(p.c.getBagNull() < 3) {
                    p.conn.sendMessageLog("Hành trang không đủ 3  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemtanghoa >= 5000) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(857));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(858));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(859));
                    Service.chatNPC(p, (short) npcid, "Anh đã đổi món quà 5000 điểm\n Yêu");   
                    p.c.diemtanghoa -= 5000;
                    break;
             }
             }
              case 1:  {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemtanghoa < 10000) {
                    Service.chatNPC(p, (short) npcid, "Anh chưa đủ 10000 điểm tặng hoa để nhận đồ xịn\n Hãy tặng em đủ 10000 bó hoa hồng\n Yêu");   
                    break;
                }
                if(p.c.getBagNull() < 3) {
                    p.conn.sendMessageLog("Hành trang không đủ 3  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemtanghoa >= 10000) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(930));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(931));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(932));
                    Service.chatNPC(p, (short) npcid, "Anh đã đổi món quà 10000 điểm\n Yêu");   
                    p.c.diemtanghoa -= 10000;
                    break;
             }
             }
               case 2:  {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemtanghoa < 15000) {
                    Service.chatNPC(p, (short) npcid, "Anh chưa đủ 15000 điểm tặng hoa để nhận đồ xịn\n Hãy tặng em đủ 15000 bó hoa hồng\n Yêu");   
                    break;
                }
                if(p.c.getBagNull() < 3) {
                    p.conn.sendMessageLog("Hành trang không đủ 3  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemtanghoa >= 15000) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(933));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(934));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(935));
                    Service.chatNPC(p, (short) npcid, "Anh đã đổi món quà 15000 điểm\n Yêu");   
                    p.c.diemtanghoa -= 15000;
                    break;
             }
             }
             }
             }
            case 2: {
               // Service.chatNPC(p, (short) npcid, "Bạn đang có: " + p.c.diemtanghoa + " điểm");   
                p.conn.sendMessageLog("Điểm tặng hoa: " + p.c.diemtanghoa);
                break;
            }
            case 3: {
                Server.manager.sendTB(p, "Hướng dẫn", "Anh cần tặng hoa cho Bulma hoặc Nami"
                        + "\nMỗi 1 bó hoa tặng sẽ nhận được 1 điểm tặng hoa\n"
                        + "Có 2 cách xem điểm 1 là chat diemtanghoa, 2 là xem tại npc Bulma hoặc Nami\n"
                        + "Khi đủ 5000 điểm sẽ nhận dc 1 set cải trang Bulma chỉ số xịn\n"
                        + "Khi đủ 10000 điểm sẽ nhận dc 1 set cải trang vegeta vô cực\n"
                        + "Khi đủ 15000 điểm sẽ nhận dc 1 set cải trang xên \n"
                        + "Chúc Anh Sớm Trở Thành Trùm VIP!");
                break;
            }
        }
    }
    

    public static void doiGiayVun(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 250);
                p.c.addItemBag(false, ItemTemplate.itemDefault(252, false));
                break;
            }
            case 1: {
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 300);
                p.c.addItemBag(false, ItemTemplate.itemDefault(253, false));
                break;
            }
        }

    }
    public static void npcThanMeo(Player p, byte npcid, byte menuId, byte b3) {
        int[] bk = {0, 397, 398, 399, 400, 401, 402};
         
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                             p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(p.c.getBagNull()< 1){
                            p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                            return;
                        }
                        if (p.luong < 30000) {
                            p.conn.sendMessageLog("Hành trang không đủ 30000 lượng");
                            return;
                        }
                        if (p.c.get().nclass == 0){
                            Service.chatNPC(p, (short)npcid, "Trùm chưa nhập học để nhận bí kíp");
                            return;
                        }
                        if (p.c.get().nclass > 0){
                            Item itemup = ItemTemplate.itemDefault(bk[p.c.get().nclass]);
                            Option op = new Option(6, (int)Util.nextInt(1, 10000));
                            itemup.options.add(op);
                            op = new Option(73, (int)Util.nextInt(1, 6000));
                            itemup.options.add(op);
                            op = new Option(69,(int) Util.nextInt(1, 50));
                            itemup.options.add(op);
                            op = new Option(81,(int) Util.nextInt(1, 500));
                            itemup.options.add(op);
                            op = new Option(119, (int)Util.nextInt(1, 5000));
                            itemup.options.add(op);
                            op = new Option(120,(int) Util.nextInt(1, 5000));
                            itemup.options.add(op);
                            op = new Option(58, (int)Util.nextInt(1, 25));
                            itemup.options.add(op);
                            op = new Option(68,(int) Util.nextInt(1, 500));
                            itemup.options.add(op);

                            itemup.sys = p.c.getSys();
                      
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(-30000);
                            break;
                        }
                }
          /*  case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                Item itemup = p.c.ItemBody[15];
                if((itemup == null)){
                    p.conn.sendMessageLog("Bạn chưa mang bí kíp");
                    return;
                }
                if(p.c.quantityItemyTotal(530) < 1000){
                    p.conn.sendMessageLog("Bạn không có 1000 linh thạch");
                    return;
                }
                if(itemup.upgrade == 16) {
                    p.conn.sendMessageLog("Bí Kíp đã đạt cấp độ tối đa");
                    return;
                }
                int[] xu = new int[16];
                xu[0] = 10000000;
                for(byte i = 1; i < 16; i++) {
                    xu[i] = xu[i - 1] + 10000000;
                }
                if(p.c.xu < xu[itemup.upgrade]){
                    p.conn.sendMessageLog("Bạn không đủ " + xu[itemup.upgrade] + " xu để nâng cấp bí kíp");
                    return;
                }
                p.c.upxuMessage(-(xu[itemup.upgrade]));
                p.c.removeItemBody((byte)15);
                for (int i = 530; i <=530 ; i++) {
                    if (p.c.getIndexBagid(i, false) != -1000) {
                        p.c.removeItemBag(p.c.getIndexBagid(i, false), 1000);
                    } else {
                        p.c.removeItemBag(p.c.getIndexBagid(i, true), 1000);
                    }
                }
                itemup.ncBK((byte)1);
                p.c.addItemBag(false, itemup);
                break;
            }*/
            case 1: {
                Server.manager.sendTB(p, "Bí Kíp", ">Bí kíp max chỉ số<\n"
                        + "Hp tối đa: 1 đến 10000\n"
                        + "Tấn công: 1 đến 6000\n"
                        + "Chí mạng: 1 đến 50\n"
                        + "Kháng tất cả: 1 đến 500\n"
                        + "Hồi hp, mp mỗi 5 giây: 1 đến 5000\n"
                        + "Cộng thêm tiềm năng: 1 đến 25%\n"
                        + "Né đòn: 1 đến 500\n"
                        );
                break;
            }
            }
        }  
    
    
//thêm public trong menu

public static void luyenma(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        if (p.c.vip < 6) {
                            Service.chatNPC(p, (short) npcid, "pro đã nạp vip 6 chưa!");
                            break;
                       
                        }
                        Map ma = Manager.getMapid(75);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                    }
                    break;
                    case 1: {
                        if (p.c.vip < 7) {
                            Service.chatNPC(p, (short) npcid, "pro đã nạp vip 12 chưa");
                            break;
                       
                        }
                        Map ma = Manager.getMapid(76);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        break;
                    }
                    case 2: {
                        if (p.c.vip < 8) {
                            Service.chatNPC(p, (short) npcid, "pro đã nạp vip 18 chưa!");
                            break;
                       
                        }
                        Map ma = Manager.getMapid(77);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        break;
                    }
                    
                    default: {
                        Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                        break;
                    }
                }
            }
            break;
            
            
            case 1: {
                Server.manager.sendTB(p, "Thông tin", "Để vào được MAP bạn cần phải đủ lv vip  tương xứng"
                        + "\n- Để vào map vip 6 bạn cần phải nạp 200k"
                        + "\n- Để vào map vip 12 bạn cần phải nạp 600k"
                        + "\n- Để vào map vip 18 bạn cần đạt 1200k"
                        + "\n- Ngoài ra:"
                        + "\n- Các võ giả muốn mạnh có 4 cách"
                        + "\n+ cách 1 nạp tiền  "
                        + "\n+ cách 2 cày chay nhưng vẫn phải nạp tiền "
                        + "\n+ buôn bán cày chay tư duy vô cực nhưng vẫn phải nạp tiền"
                        + "\n+ cách 4 làm theo 3 cách như trên");
            }
            break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }
    //Đổi vk top
    public static void menuDoiVK(Player p, byte npcid, byte menuId, byte b3) {
        int[] ids = {0, 632, 633, 634, 635, 636, 637};
        switch(menuId) {         
            case 0: {
                if(p.c.get().nclass == 0){
                    p.conn.sendMessageLog("Bạn cần nhập học để sử dụng");
                    return;
                }
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.getBagNull()< 1){
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if(p.c.quantityItemyTotal(839) < 300){
                    p.conn.sendMessageLog("Bạn không có 300 mảnh Thần Binh");
                    return;
                }
                Item itemup = ItemTemplate.itemDefault(ids[p.c.get().nclass]);
                itemup.NhanVKTop(300);
                itemup.sys = p.c.getSys();
                itemup.upgradeNext((byte)16);
                p.c.addItemBag(false, itemup);
                p.c.removeItemBags(839, 300);
                break;
            }
            case 1: {
               if(p.c.get().nclass == 0){
                    p.conn.sendMessageLog("Bạn cần nhập học để sử dụng");
                    return;
                }
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.getBagNull()< 1){
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if(p.c.quantityItemyTotal(839) < 500){
                    p.conn.sendMessageLog("Bạn không có 500 mảnh Thần Binh");
                    return;
                }
                Item itemup = ItemTemplate.itemDefault(ids[p.c.get().nclass]);
                itemup.NhanVKTop(500);
                p.c.addItemBag(false, itemup);
                itemup.sys = p.c.getSys();
                itemup.upgradeNext((byte)16);
                p.c.removeItemBags(839, 500);
                break;
            }
        }
    }
    
   
    public static void npcKanata(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.requestItem(2);
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (!p.c.clan.clanName.isEmpty()) {
                            Service.chatNPC(p, (short) npcid, "Hiện tại con đã có gia tộc, không thể thành lập gia tộc được nữa.");
                            return;
                        }

                        if (p.luong < 100000) {
                            Service.chatNPC(p, (short) npcid, "Để thành lập gia tộc, con phải có ít nhất 100000 lượng trong người.");
                            return;
                        }
                        Menu.sendWrite(p, (short) 50, "Tên gia tộc");
                        return;
                    }
                    case 1: {
                        if (p.c.clan.clanName.isEmpty()) {
                            Service.chatNPC(p, (short) npcid, "Hiện tại con chưa có gia tộc, không thể mở Lãnh địa gia tộc.");
                            return;
                        }

                        LanhDiaGiaToc lanhDiaGiaToc = null;
                        if (p.c.ldgtID != -1) {
                            if (LanhDiaGiaToc.ldgts.containsKey(p.c.ldgtID)) {
                                lanhDiaGiaToc = LanhDiaGiaToc.ldgts.get(p.c.ldgtID);
                                if (lanhDiaGiaToc != null && lanhDiaGiaToc.map[0] != null && lanhDiaGiaToc.map[0].area[0] != null) {
                                    if(lanhDiaGiaToc.ninjas.size() <= 24) {
                                        p.c.mapKanata = p.c.mapid;
                                        p.c.tileMap.leave(p);
                                        lanhDiaGiaToc.map[0].area[0].EnterMap0(p.c);
                                        return;
                                    } else {
                                        p.sendAddchatYellow("Số thành viên tham gia Lãnh Địa Gia Tộc đã đạt tối đa.");
                                    }
                                }
                            }
                        }
                        if(lanhDiaGiaToc == null){
                            if(p.c.clan.typeclan < 3) {
                                Service.chatNPC(p, (short) npcid, "Con không phải tộc trưởng hoặc tộc phó, không thể mở Lãnh địa gia tộc.");
                                return;
                            }
                            if(p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, "Hành trang của con không đủ chỗ trống để nhận Chìa khoá LDGT");
                                return;
                            }
                            ClanManager clan = ClanManager.getClanName(p.c.clan.clanName);
                            if (clan != null && p.c.clan.typeclan >= 3) {
                                if(clan.openDun <= 0) {
                                    Service.chatNPC(p, (short) npcid, "Số lần vào LDGT tuần này đã hết.");
                                    return;
                                }
                                if(clan.ldgtID != -1) {
                                    Service.chatNPC(p, (short) npcid, "Lãnh địa gia tộc của con đang được mở rồi.");
                                    return;
                                }
                                clan.openDun--;
                                clan.flush();
                                lanhDiaGiaToc = new LanhDiaGiaToc();

                                Item itemup = ItemTemplate.itemDefault(260);
                                itemup.quantity = 1;
                                itemup.expires = System.currentTimeMillis() + 600000L;
                                itemup.isExpires = true;
                                itemup.isLock = true;
                                if(p.c.quantityItemyTotal(260) > 0) {
                                    p.c.removeItemBags(260, p.c.quantityItemyTotal(260));
                                }
                                p.c.addItemBag(false, itemup);
                                p.c.ldgtID = lanhDiaGiaToc.ldgtID;
                                clan.ldgtID = lanhDiaGiaToc.ldgtID;
                                lanhDiaGiaToc.clanManager = clan;
                                p.c.mapKanata = p.c.mapid;
                                p.c.tileMap.leave(p);
                                lanhDiaGiaToc.map[0].area[0].EnterMap0(p.c);
                                return;
                            }

                        }
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog("Chức năng này không dành cho phân thân");
                            return;
                        }
                        if(p.c.quantityItemyTotal(262) < 50) {
                            Service.chatNPC(p, (short) npcid, "Con cần có 50 Đồng tiền gia tộc để đổi lấy Túi quà gia tộc.");
                            return;
                        }
                        if(p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        p.c.removeItemBags(262, 500);
                        Item itemup = ItemTemplate.itemDefault(263);
                        itemup.quantity = 1;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3:
                    default: {
                        Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật");
                        break;
                    }
                }
                break;
            }
            case 2: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog("Chức năng này không dành cho phân thân");
                    return;
                }

                //Trả thưởng
                if (b3 == 0) {
                    Service.evaluateCave(p.c);
                    return;
                }

                Cave cave = null;
                if (p.c.caveID != -1) {
                    if (Cave.caves.containsKey(p.c.caveID)) {
                        cave = Cave.caves.get(p.c.caveID);
                        if (cave != null && cave.map[0] != null && cave.map[0].area[0] != null) {
                            p.c.mapKanata = p.c.mapid;
                            p.c.tileMap.leave(p);
                            cave.map[0].area[0].EnterMap0(p.c);
                        }
                    }
                } else if (p.c.party != null && p.c.party.cave == null && p.c.party.charID != p.c.id) {
                    p.conn.sendMessageLog("Chỉ có nhóm trưởng mới được phép mở cửa hang động");
                    return;
                }

                if (cave == null) {
                    if (p.c.nCave <= 0) {
                        Service.chatNPC(p, (short) npcid, "Số lần vào hang động của con hôm nay đã hết, hãy quay lại vào ngày mai.");
                        return;
                    }
                    if (b3 == 1) {
                        if (p.c.level < 30 || p.c.level > 39) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 30 || p.c.party.aChar.get(i).level > 39) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(3);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(3);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 1;
                    }
                    if (b3 == 2) {
                        if (p.c.level < 40 || p.c.level > 49) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 40 || p.c.party.aChar.get(i).level > 49) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(4);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(4);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 3) {
                        if (p.c.level < 50 || p.c.level > 59) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 50 || p.c.party.aChar.get(i).level > 59) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(5);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(5);
                        }
                        p.c.caveID = cave.caveID;
                    }
                    if (b3 == 4) {
                        if (p.c.level < 60 || p.c.level > 69) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null && p.c.party.aChar.size() > 1) {
                            p.conn.sendMessageLog("Hoạt động này chỉ được phép 1 mình.");
                            return;
                        }
                        cave = new Cave(6);
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 1;
                    }
                    if (b3 == 5) {
                        if (p.c.level < 70 || p.c.level > 89) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 70 || p.c.party.aChar.get(i).level > 89) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(7);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(7);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 6) {
                        if (p.c.level < 90 || p.c.level > 130) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 90 || p.c.party.aChar.get(i).level > 131) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(9);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(9);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
if (b3 == 7) {
                        if (p.c.level < 130 || p.c.level > 150) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 130 || p.c.party.aChar.get(i).level > 151) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(8);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(8);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (cave != null) {
                        p.c.nCave--;
                        p.c.pointCave = 0;

                        if (p.c.party != null && p.c.party.charID == p.c.id) {
                            if(p.c.party.aChar != null && p.c.party.aChar.size() > 0) {
                                synchronized (p.c.party.aChar) {
                                    Char _char;
                                    for (int i = 0; i < p.c.party.aChar.size(); i++) {
                                        if(p.c.party.aChar.get(i) != null) {
                                            _char = p.c.party.aChar.get(i);
                                            if (_char.id != p.c.id && p.c.tileMap.getNinja(_char.id) != null && _char.nCave > 0 && _char.caveID == -1 && _char.level >= 30 && (int) _char.level / 10 == cave.x) {
                                                _char.nCave--;
                                                _char.pointCave = 0;
                                                _char.caveID = p.c.caveID;
                                                _char.isHangDong6x = p.c.isHangDong6x;
                                                _char.mapKanata = _char.mapid;
                                                _char.countHangDong++;
                                                if (_char.pointUydanh < 5000) {
                                                    _char.pointUydanh += 5;
                                                }
                                                _char.tileMap.leave(_char.p);
                                                cave.map[0].area[0].EnterMap0(_char);
                                                _char.p.setPointPB(_char.pointCave);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        p.c.mapKanata = p.c.mapid;
                        p.c.countHangDong++;
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 5;
                        }
                        p.c.tileMap.leave(p);
                        cave.map[0].area[0].EnterMap0(p.c);
                    }
                }
                p.setPointPB(p.c.pointCave);
                break;
            }
            case 3: {
//                Service.chatNPC(p, (short) npcid, "Chức năng đang bảo trì, vui lòng quay lại sau!");
//                return;
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.party != null && p.c.party.charID != p.c.id) {
                            Service.chatNPC(p, (short) npcid, "Con không phải trưởng nhóm, không thể thực hiện gửi lời mời lôi đài cho người/nhóm khác");
                            return;
                        }

                        Service.sendInputDialog(p, (short) 2, "Nhập tên đối thủ của con");
                        return;
                    }
                    case 1: {
                        Service.sendLoiDaiList(p.c);
                        return;
                    }
                    case 2: {
                        String alert = "";

                        for (int i = 0; i < DunListWin.dunList.size(); ++i) {
                            int temp = i + 1;
                            alert = alert + temp + ". Phe " + ((DunListWin) DunListWin.dunList.get(i)).win + " thắng Phe " + ((DunListWin) DunListWin.dunList.get(i)).lose + ".\n";
                        }
                        Server.manager.sendTB(p, "Kết quả", alert);
                        return;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                Service.chatNPC(p, (short) npcid, "Vũ khí của ta cực sắc bén. Nếu muốn tỷ thí thì cứ đến chỗ ta!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật");
                break;
            }
        }
    }

    public static void npcFuroya(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                switch(b3) {
                    case 0:
                        p.requestItem(21 - p.c.gender);
                        return;
                    case 1:
                        p.requestItem(23 - p.c.gender);
                        return;
                    case 2:
                        p.requestItem(25 - p.c.gender);
                        return;
                    case 3:
                        p.requestItem(27 - p.c.gender);
                        return;
                    case 4:
                        p.requestItem(29 - p.c.gender);
                        return;
                    default:
                        Service.chatNPC(p, (short)npcid, "Chức năng này đang cập nhật!");
                        return;
                }
            case 1:
                Service.chatNPC(p, (short)npcid, "Tan bán quần áo, mũ nón, găng tay và giày siêu bền, siêu rẻ!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }
static void npccasino(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu > 10000000) {
                            p.c.upxuMessage(-10000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(19000000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 20.000.000 xu của Casino nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Casino Luộc 10.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.xu > 10000000) {
                            p.c.upxuMessage(-10000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(19000000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 20.000.000 xu của Casino nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Casino Luộc 10.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu > 50000000) {
                            p.c.upxuMessage(-50000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(90000000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 100.000.000 xu của Casino nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Casino Luộc 50.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.xu > 50000000) {
                            p.c.upxuMessage(-50000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(90000000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 100.000.000 xu của Casino nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Casino Luộc 50.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu > 100000000) {
                            p.c.upxuMessage(-100000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(190000000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 200.000.000 xu của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Casino Luộc 50.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.xu > 100000000) {
                            p.c.upxuMessage(-100000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(190000000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 200.000.000 xu của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Casino Luộc 100.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.luong > 10000) {
                            p.upluongMessage(-10000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(19000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 20.000 lượng của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẽ con nghiện " + p.c.name + " vừa bị Casino Luộc 10.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.luong > 10000) {
                            p.upluongMessage(-10000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(19000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 19.000 lượng của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Casino Luộc 10.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 4: {
                switch (b3) {
                    case 0: {
                        if (p.luong > 50000) {
                            p.upluongMessage(-50000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(90000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 100.000 lượng của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẽ con nghiện " + p.c.name + " vừa bị Casino Luộc 50.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.luong > 50000) {
                            p.upluongMessage(-50000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(90000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 100.000 lượng của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Casino Luộc 50.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 5: {
                switch (b3) {
                    case 0: {
                        if (p.luong > 100000) {
                            p.upluongMessage(-100000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(190000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 200.000 lượng của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Casino Luộc 100.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.luong > 100000) {
                            p.upluongMessage(-100000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(190000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 200.000 lượng của Casino Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Casino Luộc 100.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            
            case 6: {
                switch (b3) {
                    case 0: {
                        if (p.c.quantityItemyTotal(632) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Vô Cực Kiếm");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(632, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Kiếm rồi");
                                final Item itemup = ItemData.itemDefault(397);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(632, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 1: {
                        if (p.c.quantityItemyTotal(633) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Thiên Hỏa Tiêu");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(633, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Tiêu rồi");
                                final Item itemup = ItemData.itemDefault(398);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(633, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 2: {
                        if (p.c.quantityItemyTotal(636) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Chiến Lục Đao");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(636, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Đao rồi");
                                final Item itemup = ItemData.itemDefault(401);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(636, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 3: {
                        if (p.c.quantityItemyTotal(637) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Hoàng Phong Phiến");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(637, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Quạt rồi");
                                final Item itemup = ItemData.itemDefault(402);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(637, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 4: {
                        if (p.c.quantityItemyTotal(634) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Táng Hồn Dao");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(634, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Kunai rồi");
                                final Item itemup = ItemData.itemDefault(399);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(634, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 5: {
                        if (p.c.quantityItemyTotal(635) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Băng Thần Cung");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(635, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Cung rồi");
                                final Item itemup = ItemData.itemDefault(400);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(635, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                        }
                    }
                }
            }
        }
          default: {
                Server.manager.sendTB(p, "Hướng dẫn", "Khoắng cây bút viết thơ tặng bạn Chúc Tân Niên có vạn niềm vui Bao nhiêu vất vả đẩy lùi Thay vào là những ngọt bùi yêu thương Hôm nay là Tết Dương lịch đó Gửi lời chúc nhờ gió chuyển cho Mong mọi người hết sầu lo Bình an hạnh phúc chuyến đò nhân gian Một... hai... ba, cùng san sẻ Tết Ta nâng ly quên hết buồn đời Chúc cho cuộc sống tuyệt vời Tình bạn tri kỷ người ơi giữ gìn Hãy đặt những niềm tin yêu quý Sống chân thành, hoan hỷ mỗi ngày Thế sự có lắm đổi thay Tâm ta bất biến, thẳng ngay mà làm Gửi chúc người Việt Nam yêu dấu Năm Tân sửu phấn đấu mọi điều Làm những công việc mình yêu Để cho cuộc sống thêm nhiều bình yên -Tết 2022 Chúc Mọi Người May Mắn !!");
                break;
            }
        }
    }

     public static void npcCLXTCoin(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch(menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 44_0_0, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 44_0_1, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckTXCoin check: CheckTXCoin.checkTXCoinArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 44_1_0, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 44_1_1, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckCLCoin check: CheckCLCoin.checkCLCoinArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                try {
                    synchronized(Server.LOCK_MYSQL) {
                        ResultSet red = SQLManager.stat.executeQuery("SELECT `coin` FROM `player` WHERE `id` = "+p.id+";");
                        if (red != null && red.first()) {
                            p.coin = red.getInt("coin");
                            p.conn.sendMessageLog("Bạn đang có : " + p.coin +". Hãy thoát ra vào lại để cập nhật coin mới nhất nếu nạp.");
                            break;
                        }
                    }
                } catch (SQLException var17) {
                    var17.printStackTrace();
                    p.conn.sendMessageLog("Lỗi.");
                }
                break;
            }
            case 3: {
                Server.manager.sendTB(p, "Hướng dẫn", "Đây là NPC chơi CLTX bằng coin."
                        + "\nMỗi lần đặt cược giá trị phải là bội số của 10 (20,30,40,...)\n"
                        + "Nếu may mắn chiến thắng bạn sẽ nhận được 1,9 số coin cược.\n"
                        + "Nếu thua bạn méo được gì.\n"
                        + "Để có coin chơi bạn cần lên web teamobi.cf nạp coin hoặc ib admin.\n"
                        + "Chúc Bạn Sớm Trở Thành Trùm VIP!");
                break;
            }
        }
    }
    
    public static void npcCLXTLuong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 45_0_0, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 45_0_1, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckTXLuong check: CheckTXLuong.checkTXLuongArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 45_1_0, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 45_1_1, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckCLLuong check: CheckCLLuong.checkCLLuongArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
    public static void npcAmeji(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        p.requestItem(16);
                        break;
                    }
                    case 1: {
                        p.requestItem(17);
                        break;
                    }
                    case 2: {
                        p.requestItem(18);
                        break;
                    }
                    case 3: {
                        p.requestItem(19);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 1: {
                ItemTemplate data;
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level < 50) {
                            Service.chatNPC(p, (short) npcid, "Cấp độ của con không đủ để nhận nhiệm vụ này");
                            return;
                        }

                        if (p.c.countTaskDanhVong < 1) {
                            Service.chatNPC(p, (short) npcid, "Số lần nhận nhiệm vụ của con hôm nay đã hết");
                            return;
                        }

                        if (p.c.isTaskDanhVong == 1) {
                            Service.chatNPC(p, (short) npcid, "Trước đó con đã nhận nhiệm vụ rồi, hãy hoàn thành đã nha");
                            return;
                        }

                        int type = DanhVongTemplate.randomNVDV();
                        p.c.taskDanhVong[0] = type;
                        p.c.taskDanhVong[1] = 0;
                        p.c.taskDanhVong[2] = DanhVongTemplate.targetTask(type);
                        p.c.isTaskDanhVong = 1;
                        p.c.countTaskDanhVong--;
                        if (p.c.isTaskDanhVong == 1) {
                            String nv = "NHIỆM VỤ LẦN NÀY: \n" +
                                    String.format(DanhVongTemplate.nameNV[p.c.taskDanhVong[0]],
                                            p.c.taskDanhVong[1],
                                            p.c.taskDanhVong[2])
                                    + "\n\n- Số lần nhận nhiệm vụ còn lại là: " + p.c.countTaskDanhVong;
                            Server.manager.sendTB(p, "Nhiệm vụ", nv);
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskDanhVong == 0) {
                            Service.chatNPC(p, (short) npcid, "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }

                        if (p.c.taskDanhVong[1] < p.c.taskDanhVong[2]) {
                            Service.chatNPC(p, (short) npcid, "Con chưa hoàn thành nhiệm vụ ta giao!");
                            return;
                        }

                        if (p.c.getBagNull() < 2) {
                            Service.chatNPC(p, (short) npcid, "Hành trang của con không đủ chỗ trống để nhận thưởng");
                            return;
                        }
                        int point = 0;
                        switch(p.c.vip){
                            case 0:{
                                point = 3;
                                break;
                            }
                            case 1:{
                                point = 50;
                                break;
                            }
                            case 2:{
                                point = 60;
                                break;
                            }
                            case 3:{
                                point = 70;
                                break;
                            }
                            case 4:{
                                point = 80;
                                break;
                            }
                            case 5:{
                                point = 90;
                                break;
                            }
                            case 6:{
                                point = 100;
                                break;
                            }
                        }
                        if (p.c.taskDanhVong[0] == 9) {
                            if(p.c.vip < 1){
                                point = 5;
                            }
                            else{
                                point = 150;
                            }
                        }

                        p.c.isTaskDanhVong = 0;
                        p.c.taskDanhVong = new int[]{-1, -1, -1, 0, p.c.countTaskDanhVong};
                        Item item = ItemTemplate.itemDefault(DanhVongTemplate.randomDaDanhVong(), false);
                        item.quantity = 1;
                        item.isLock = false;
                        if (p.c.pointUydanh < 5000) {
                            ++p.c.pointUydanh;
                        }

                        p.c.addItemBag(true, item);
                        int type = Util.nextInt(10);

                        if (p.c.avgPointDanhVong(p.c.getPointDanhVong(type))) {
                            for (int i = 0; i < 10; i++) {
                                type = i;
                                if (!p.c.avgPointDanhVong(p.c.getPointDanhVong(type))) {
                                    break;
                                }
                            }
                        }
                        p.c.plusPointDanhVong(type, point);

                        if(p.c.countTaskDanhVong % 2 == 0) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 739 : 766, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = (int)Util.nextInt(1,2);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 740 : 767, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = (int)Util.nextInt(1,2);
                            p.c.addItemBag(true, itemUp);
                        }

                        Service.chatNPC(p, (short) npcid, "Con hãy nhận lấy phần thưởng của mình.");
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskDanhVong == 0) {
                            Service.chatNPC(p, (short) npcid, "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }

                        Service.startYesNoDlg(p, (byte) 2, "Con có chắc chắn muốn huỷ nhiệm vụ lần này không?");
                        break;
                    }
                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.checkPointDanhVong(1)) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, "Hành trang của con không đủ chỗ trống để nhận thưởng");
                                return;
                            }

                            Item item = ItemTemplate.itemDefault(685, true);
                            item.quantity = 1;
                            item.upgrade = 1;
                            item.isLock = true;
                            Option op = new Option(6, 1000);
                            item.options.add(op);
                            op = new Option(87, 500);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                        } else {
                            Service.chatNPC(p, (short) npcid, "Con chưa đủ điểm để nhận mắt");
                        }

                        break;
                    }
               /*     case 4: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.ItemBody[14] == null) {
                            Service.chatNPC(p, (short) npcid, "Hãy đeo mắt vào người trước rồi nâng cấp nhé.");
                            return;
                        }

                        if (p.c.ItemBody[14] == null) {
                            return;
                        }

                        if (p.c.ItemBody[14].upgrade >= 10) {
                            Service.chatNPC(p, (short) npcid, "Mắt của con đã đạt cấp tối đa");
                            return;
                        }

                        if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                            Service.chatNPC(p, (short) npcid, "Con chưa đủ điểm danh vọng để thực hiện nâng cấp");
                            return;
                        }

                        data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                        Service.startYesNoDlg(p, (byte) 0, "Bạn có muốn nâng cấp " + data.name + " với " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " yên hoặc xu với tỷ lệ thành công là " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] + "% không?");
                        break;
                    }
                    case 5: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.ItemBody[14] == null) {
                            Service.chatNPC(p, (short) npcid, "Hãy đeo mắt vào người trước rồi nâng cấp nhé.");
                            return;
                        }

                        if (p.c.ItemBody[14].upgrade >= 10) {
                            Service.chatNPC(p, (short) npcid, "Mắt của con đã đạt cấp tối đa");
                            return;
                        }

                        if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                            Service.chatNPC(p, (short) npcid, "Con chưa đủ điểm danh vọng để thực hiện nâng cấp");
                            return;
                        }

                        data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                        Service.startYesNoDlg(p, (byte) 1, "Bạn có muốn nâng cấp " + data.name + " với " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " yên hoặc xu và " + GameSrc.goldUpMat[p.c.ItemBody[14].upgrade] + " lượng với tỷ lệ thành công là " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] * 2 + "% không?");
                        break;
                    }*/
                    case 4: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        String nv = "- Hoàn thành nhiệm vụ. Hãy gặp Ameji để trả nhiệm vụ.\n- Hôm nay có thể nhận thêm " + p.c.countTaskDanhVong + " nhiệm vụ trong ngày.\n- Hôm nay có thể sử dụng thêm " + p.c.useDanhVongPhu + " Danh Vọng Phù để nhận thêm 5 lần làm nhiệm vụ.\n- Hoàn thành nhiệm vụ sẽ nhận ngẫu nhiên 1 viên đá danh vọng cấp 1-10.\n- Khi đủ mốc 100 điểm mỗi loại có thể nhận mắt và nâng cấp mắt.";
                        if (p.c.isTaskDanhVong == 1) {
                            nv = "NHIỆM VỤ LẦN NÀY: \n" + String.format(DanhVongTemplate.nameNV[p.c.taskDanhVong[0]], p.c.taskDanhVong[1], p.c.taskDanhVong[2]) + "\n\n" + nv;
                        }

                        Server.manager.sendTB(p, "Nhiệm vụ", nv);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 2: {
                Service.chatNPC(p, (short) npcid, "Tan bán các loại trang sức lấp lánh!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcKiriko(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(7);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(6);
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTabemono(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                p.requestItem(9);
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(8);
                break;
            case 2: {
                Service.chatNPC(p, (short) npcid, "3 đời nhà ta bán thức ăn chưa ai bị đau bụng cả!");
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                switch (b3) {
                    case 0: {
                        if (!ThienDiaBangManager.register) {
                            Service.chatNPC(p, (short) npcid, "Đang trong thời gian tổng kết. Hiện tại không thể đăng ký.");
                            return;
                        }
                        if (ThienDiaBangManager.diaBangList.containsKey(p.c.name) || ThienDiaBangManager.thienBangList.containsKey(p.c.name)) {
                            Service.chatNPC(p, (short) npcid, "Con đã đăng ký trước đó rồi");
                            return;
                        }
                        if (p.c.get().level >= 50 && p.c.get().level < 70) {
                            ThienDiaBangManager.diaBangList.put(p.c.name, new ThienDiaData(p.c.name, ThienDiaBangManager.rankDiaBang++, 1));
                            Service.chatNPC(p, (short) npcid, "Con đã đăng ký tham gia trang tài Địa bảng thành công.");
                        } else if (p.c.get().level >= 70) {
                            ThienDiaBangManager.thienBangList.put(p.c.name, new ThienDiaData(p.c.name, ThienDiaBangManager.rankThienBang++, 1));
                            Service.chatNPC(p, (short) npcid, "Con đã đăng ký tham gia tranh tài Thiên bảng thành công.");
                        } else {
                            Service.chatNPC(p, (short) npcid, "Trình độ của con không phù hợp để đăng ký tham gia tranh tài.");
                        }
                        break;
                    }
                    case 1: {
                        if (!ThienDiaBangManager.register) {
                            Service.chatNPC(p, (short) npcid, "Đang trong thời gian tổng kết. Hiện tại không thể thi đấu.");
                            return;
                        }
                        ArrayList<ThienDiaData> list = new ArrayList<>();
                        if (ThienDiaBangManager.diaBangList.containsKey(p.c.name)) {
                            ThienDiaData rank = ThienDiaBangManager.diaBangList.get(p.c.name);
                            for (ThienDiaData data : ThienDiaBangManager.getListDiaBang()) {
                                if (data != null) {
                                    if (rank.getRank() < 10 && (data.getRank() - rank.getRank()) < 20) {
                                        list.add(data);
                                    } else if (data.getRank() < rank.getRank() & (rank.getRank() - data.getRank()) < 10) {
                                        list.add(data);
                                    }
                                }
                            }
                        } else if (ThienDiaBangManager.thienBangList.containsKey(p.c.name)) {
                            ThienDiaData rank = ThienDiaBangManager.thienBangList.get(p.c.name);
                            for (ThienDiaData data : ThienDiaBangManager.getListThienBang()) {
                                if (data != null) {
                                    if (rank.getRank() < 10 && (data.getRank() - rank.getRank()) < 20) {
                                        list.add(data);
                                    } else if (data.getRank() <= rank.getRank() & (rank.getRank() - data.getRank()) < 10) {
                                        list.add(data);
                                    }
                                }
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Con chưa đăng ký tham gia thi đấu.");
                            return;
                        }
                        Service.SendChinhPhuc(p, list);
                        return;
                    }
                    case 2: {
                        String res = "";
                        int count = 1;
                        for (ThienDiaData data : ThienDiaBangManager.getListSortAsc(new ArrayList<ThienDiaData>(ThienDiaBangManager.thienBangList.values()))) {
                            if (count < 11) {
                                res += "Hạng " + count + ": " + data.getName() + ".\n";
                                count++;
                            }
                        }
                        Server.manager.sendTB(p, "Thiên bảng", res);
                        return;
                    }
                    case 3: {
                        String res = "";
                        int count = 1;
                        for (ThienDiaData data : ThienDiaBangManager.getListSortAsc(new ArrayList<ThienDiaData>(ThienDiaBangManager.diaBangList.values()))) {
                            if (count < 11) {
                                res += "Hạng " + count + ": " + data.getName() + ".\n";
                                count++;
                            }
                        }
                        Server.manager.sendTB(p, "Địa bảng", res);
                        return;
                    }
                    case 4: {
                        ResultSet res = null;
                        try {
                        if (true){//if(Manager.nhanquatdb == 0){
                           Service.chatNPC(p, (short) npcid, "Chỉ nhận quà được vào thứ 2.");
                                return;
                            }
                            if(p.c.rankTDB > 0) {
                                if(p.c.isGiftTDB == 1) {
                                    if(p.c.rankTDB > 20) {
                                        p.upluongMessage(500);
                                        p.c.upxuMessage(500000);
                                    } else {
                                        switch (p.c.rankTDB) {
                                            case 1: {
                                                if(p.c.getBagNull() < 10) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 10 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                Item pl = ItemTemplate.itemDefault(308,false);
                                                pl.quantity = 2;
                                                p.c.addItemBag(true,pl);

                                                pl = ItemTemplate.itemDefault(309,false);
                                                pl.quantity = 2;
                                                p.c.addItemBag(true,pl);

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(384,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));

                                                p.upluongMessage(20000);
                                                p.c.upxuMessage(20000000);
                                                break;
                                            }
                                            case 2: {
                                                if(p.c.getBagNull() < 7) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 7 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                Item pl = ItemTemplate.itemDefault(308,false);
                                                pl.quantity = 1;
                                                p.c.addItemBag(true,pl);

                                                pl = ItemTemplate.itemDefault(309,false);
                                                pl.quantity = 1;
                                                p.c.addItemBag(true,pl);

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));

                                                p.c.addItemBag(false,ItemTemplate.itemDefault(384,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));

                                                p.upluongMessage(10000);
                                                p.c.upxuMessage(10000000);
                                                break;
                                            }
                                            case 3: {
                                                if(p.c.getBagNull() < 4) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 4 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(540,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.upluongMessage(5000);
                                                p.c.upxuMessage(5000000);
                                                break;
                                            }
                                            case 4:
                                            case 5:
                                            case 6:
                                            case 7:
                                            case 8:
                                            case 9:
                                            case 10: {
                                                if(p.c.getBagNull() < 4) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 2 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(539,false));
                                                p.c.addItemBag(false,ItemTemplate.itemDefault(383,false));
                                                p.upluongMessage(3000);
                                                p.c.upxuMessage(3000000);
                                                break;
                                            }
                                            case 11:
                                            case 12:
                                            case 13:
                                            case 14:
                                            case 15:
                                            case 16:
                                            case 17:
                                            case 18:
                                            case 19:
                                            case 20: {
                                                p.upluongMessage(1000);
                                                p.c.upxuMessage(1000000);
                                                break;
                                            }

                                        }
                                    }
                                    p.c.isGiftTDB = 0;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Con đã nhận thưởng tuần rồi.");
                                    return;
                                }
                            } else {
                                Service.chatNPC(p, (short) npcid, "Tuần trước con chưa tham gia Thiên Địa bảng và chưa có rank, con chưa thể nhận thường.");
                                return;
                            }
                        } catch (Exception e) {
                            p.conn.sendMessageLog("Lỗi nhận thưởng, vui lòng thử lại sau!");
                            return;
                        } finally {
                            if(res != null) {
                                try {
                                    res.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    }
                    case 5: {
                        Server.manager.sendTB(p, "Hướng dẫn", "- Thiên Địa Bảng sẽ được mở hàng tuần. Bắt đầu từ thứ 2 và tổng kết vào chủ nhật.\n" +
                                "- Thiên Địa Bảng sẽ được mở đăng ký và chính phục từ 00h05' đến 23h45' hàng ngày. Mỗi ngày sẽ có 20p để tổng kết ngày, trong thời gian này sẽ không thể đăng ký và chinh phục\n" +
                                "- Trong thời gian tổng kết nếu chiến thắng trong Chinh phục sẽ không được tính rank." +
                                "- Vào ngày thường sẽ không giới hạn lượt thách đấu.\n" +
                                "- Vào Thứ 7 và Chủ Nhật mỗi Ninja sẽ có 5 lượt thách đấu, Thắng sẽ không bị mất lượt, thua sẽ bị trừ 1 lần thách đấu." +
                                "- Địa Bảng dành cho ninja từ cấp độ 50-69.\n" +
                                "- Thiên Bảng dành cho ninja từ cấp độ trên 70\n" +
                                "- Sau khi đăng ký thành công, hãy Chinh Phục ngay để giành lấy vị trí top đầu.\n" +
                                "- Mỗi lần chiến thắng, nếu vị trí của đối thủ trước bạn, bạn sẽ đổi vị trí của mình cho đối thủ, còn không vị trí của bạn sẽ được giữ nguyên.\n" +
                                "- Phần thưởng sẽ được trả thưởng vào mỗi tuần mới (Lưu ý: Hãy nhận thưởng ngay trong tuần mới đó, nếu sang tuần sau phần thưởng sẽ bị reset).\n\n" +
                                "- PHẦN THƯỞNG: \n" +
                                "Top 1: Hào quang Rank 1 + 2 Bánh Phong Lôi, 2 Bánh Băng Hoả, 2 Nấm x4, 3 Nấm x3, 1 Rương bạch ngân, 2 Bát bảo, 20,000 Lượng, 20,000,000 xu.\n\n" +
                                "Top 2: Hào quang Rank 2 + 1 Bánh Phong Lôi, 1 Bánh Băng Hoả, 1 Nấm x4, 2 Nấm x3, 1 Rương bạch ngân, 1 Bát bảo, 10,000 Lượng, 10,000,000 xu.\n\n" +
                                "Top 3: Hào quang Rank 3 + 1 Nấm x4, 1 Nấm x3, 2 Bát bảo, 5,000 Lượng, 5,000,000 xu.\n\n" +
                                "Top 4-10: 1 Nấm x3, 1 Bát bảo, 3,000 Lượng, 3,000,000 xu.\n\n" +
                                "Top 11-20: 1,000 Lượng, 1,000,000 xu.\n\n" +
                                "Còn lại: 500 Lượng, 500,000 xu.");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcKamakura(Player p, byte npcid, byte menuId, byte b3) {
        try {
           
            switch(menuId) {
                case 0:
                    //p.requestItem(4);
                    switch (b3) {
                        case 0: {
                            Service.openMenuBox(p);
                            break;
                        }
                        case 1: {
                            Service.openMenuBST(p);
                            break;
                        }
                        case 2: {
                            Service.openMenuCaiTrang(p);
                            break;
                        }
                        case 3: {
                            //Tháo cải trang
                            p.c.caiTrang = -1;
                            Message m = new Message(11);
                            m.writer().writeByte(-1);
                            m.writer().writeByte((int) p.c.get().speed());
                            m.writer().writeInt((int) p.c.get().getMaxHP());
                            m.writer().writeInt((int) p.c.get().getMaxMP());
                            m.writer().writeShort((int) p.c.get().eff5buffHP());
                            m.writer().writeShort((int) p.c.get().eff5buffMP());
                            m.writer().flush();
                            p.conn.sendMessage(m);
                            m.cleanup();
                            Service.CharViewInfo(p, false);
                            p.endLoad(true);
                            break;
                        }
                    }
                    break;
                case 1:
                    if(p.c.tileMap.map.getXHD() != -1 || p.c.tileMap.map.LangCo() || p.c.tileMap.map.mapBossTuanLoc() || p.c.tileMap.map.mapLDGT() || p.c.tileMap.map.mapGTC() || p.c.tileMap.map.id == 111 || p.c.tileMap.map.id == 113) {
                        p.c.mapLTD = 22;
                    } else {
                        p.c.mapLTD = p.c.tileMap.map.id;
                    }
                    Service.chatNPC(p, (short)npcid, "Lưu toạ độ thành công! Khi chết con sẽ được vác xác về đây.");
                    break;
                case 2:
                    switch(b3) {
                        case 0:
                            if (p.c.level < 60) {
                                p.conn.sendMessageLog("Chức năng này yên cầu trình độ 60");
                                return;
                            }

                            Map ma = Manager.getMapid(139);
                            TileMap area;
                            int var8;
                            for(var8 = 0; var8 < ma.area.length; ++var8) {
                                area = ma.area[var8];
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                            return;
                        case 1:
                            Server.manager.sendTB(p, "Hướng dẫn", "- Hướng dẫn vùng đất ma quỷ");
                            return;
                        default:
                            return;
                    }
                case 3:
                    Service.chatNPC(p, (short)npcid, "Ta giữ đồ chưa bao giờ bị mất thứ gì cả!");
                    break;
                default: {
                    Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void npcKenshinto(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        if(p.c.isNhanban) {
            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
            return;
        }
        switch(menuId) {
            case 0: {
                if (b3 == 0) {
                    p.requestItem(10);
                } else if (b3 == 1) {
                    p.requestItem(31);
                } else if (b3 == 2) {
                    Server.manager.sendTB(p, "Hướng dẫn", "- Hướng dẫn nâng cấp trang bị");
                }
                break;
            }
            case 1: {
                if (b3 == 0) {
                    p.requestItem(12);
                } else if (b3 == 1) {
                    p.requestItem(11);
                }
                break;
            }
            case 2: {
                p.requestItem(13);
                break;
            }
            case 3: {
                p.requestItem(33);
                break;
            }
            case 4: {
                p.requestItem(46);
                break;
            }
            case 5: {
                p.requestItem(47);
                break;
            }
            case 6: {
                p.requestItem(49);
                break;
            }
            case 7: {
                p.requestItem(50);
                break;
            }
            case 8: {
                Service.chatNPC(p, (short) npcid, "Cần nâng cấp trang bị, hãy đến quán của ta!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcUmayaki_Lang(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ta kéo xe qua các làng với tốc độ ánh sáng!");
                return;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                TileMap[] var5 = Manager.getMapid(Map.arrLang[menuId - 1]).area;
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    TileMap area = var5[var7];
                    if (area.numplayers < Manager.getMapid(Map.arrLang[menuId - 1]).template.maxplayers) {
                        p.c.tileMap.leave(p);
                        area.EnterMap0(p.c);
                        return;
                    }
                }
                case 8:{
                p.c.tileMap.leave(p);
                        Map map = Server.maps[170];
                        byte k;
                        for (k = 0; k < map.area.length; k++) {
                            if (map.area[k].numplayers < map.template.maxplayers) {
                               map.area[k].EnterMap0(p.c);
                                break;
                            }
                        }
                        p.endLoad(true);
                        break;}
                case 9:{
                p.c.tileMap.leave(p);
                        Map map = Server.maps[179];
                        byte k;
                        for (k = 0; k < map.area.length; k++) {
                            if (map.area[k].numplayers < map.template.maxplayers) {
                               map.area[k].EnterMap0(p.c);
                                break;
                            }
                        }
                        p.endLoad(true);
                        break;}
                    
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcUmayaki_Truong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
            case 1:
            case 2:
                TileMap[] var5 = Manager.getMapid(Map.arrTruong[menuId]).area;
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    TileMap area = var5[var7];
                    if (area.numplayers < Manager.getMapid(Map.arrTruong[menuId]).template.maxplayers) {
                        p.c.tileMap.leave(p);
                        area.EnterMap0(p.c);
                        return;
                    }
                }

                return;
            case 3:
                Service.chatNPC(p, (short)npcid, "Ta kéo xe qua các trường, không qua quán net đâu!");
                return;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcToyotomi(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                switch(b3) {
                    case 0:
                        Server.manager.sendTB(p, "Top đại gia yên", Rank.getStringBXH(0));
                        return;
                    case 1:
                        Server.manager.sendTB(p, "Top cao thủ", Rank.getStringBXH(1));
                        return;
                    case 2:
                        Server.manager.sendTB(p, "Top gia tộc", Rank.getStringBXH(2));
                        return;
                    case 3:
                        Server.manager.sendTB(p, "Top hang động", Rank.getStringBXH(3));
                        return;
                    default:
                        return;
                }
            case 1:
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, (short)npcid, "Con đã vào lớp từ trước rồi mà.");
                } else if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, (short)npcid, "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                } else if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, (short)npcid, "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                } else {
                    if (b3 == 0) {
                        Admission.Admission(p,(byte)1);
                    } else {
                        if (b3 != 1) {
                            Service.chatNPC(p, (short)npcid, "Chức năng này đang cập nhật!");
                            break;
                        }
                        Admission.Admission(p,(byte)2);
                    }

                    Service.chatNPC(p, (short)npcid, "Hãy chăm chỉ luyện tập, có làm thì mới có ăn con nhé.");
                }
                break;
            case 2:
                if (p.c.get().nclass != 1 && p.c.get().nclass != 2) {
                    Service.chatNPC(p, (short)npcid, "Con không phải học sinh của trường này, ta không thể giúp con tẩy điểm dược rồi.");
                } else if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, (short)npcid, "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, (short)npcid, "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                } else if (b3 == 1) {
                    if (p.c.get().countTayKyNang < 1) {
                        Service.chatNPC(p, (short)npcid, "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }
                    p.restSpoint();
                    --p.c.get().countTayKyNang;
                    Service.chatNPC(p, (short)npcid, "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                }
                break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Trường ta là 1 ngôi trường danh giá, chỉ giành cho nhưng ninja tính nóng như kem mà thôi.");
                break;
            case 4:
                Service.chatNPC(p, (short)npcid, "Ta đang hơi mệt xíu, ta sẽ giao chiến với con sau nha! Bye bye...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcOokamesama(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:

                switch(b3) {
                    case 0:

                        Server.manager.sendTB(p, "Top đại gia yên", Rank.getStringBXH(0));
                        return;
                    case 1:

                        Server.manager.sendTB(p, "Top cao thủ", Rank.getStringBXH(1));
                        return;
                    case 2:

                        Server.manager.sendTB(p, "Top gia tộc", Rank.getStringBXH(2));
                        return;
                    case 3:

                        Server.manager.sendTB(p, "Top hang động", Rank.getStringBXH(3));
                        return;
                    default:
                        return;
                }
            case 1:
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, (short)npcid, "Con đã vào lớp từ trước rồi mà.");
                } else if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, (short)npcid, "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                } else if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, (short)npcid, "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                } else {
                    if (b3 == 0) {
                        Admission.Admission(p,(byte)3);
                    } else {
                        if (b3 != 1) {
                            Service.chatNPC(p, (short)npcid, "Chức năng này đang cập nhật!");
                            break;
                        }

                        Admission.Admission(p,(byte)4);
                    }

                    Service.chatNPC(p, (short)npcid, "Hãy chăm chỉ luyện tập, có làm thì mới có ăn con nhé.");
                }
                break;
            case 2:
                if (p.c.get().nclass != 3 && p.c.get().nclass != 4) {
                    Service.chatNPC(p, (short)npcid, "Con không phải học sinh của trường này, ta không thể giúp con tẩy điểm dược rồi.");
                } else if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, (short)npcid, "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, (short)npcid, "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                } else if (b3 == 1) {
                    if (p.c.get().countTayKyNang < 1) {
                        Service.chatNPC(p, (short)npcid, "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }

                    p.restSpoint();
                    --p.c.get().countTayKyNang;
                    Service.chatNPC(p, (short)npcid, "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                }
                break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Sao hôm nay trời nóng thế nhỉ, hình như biến đổi khí hậu làm tan hết băng trường ta rồi!");
                break;
            case 4:
                Service.chatNPC(p, (short)npcid, "Ta đang hơi mệt xíu, ta sẽ giao chiến với con sau nha! Bye bye...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcKazeto(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                switch(b3) {
                    case 0:

                        Server.manager.sendTB(p, "Top đại gia yên", Rank.getStringBXH(0));
                        return;
                    case 1:

                        Server.manager.sendTB(p, "Top cao thủ", Rank.getStringBXH(1));
                        return;
                    case 2:

                        Server.manager.sendTB(p, "Top gia tộc", Rank.getStringBXH(2));
                        return;
                    case 3:

                        Server.manager.sendTB(p, "Top hang động", Rank.getStringBXH(3));
                        return;
                    default:
                        return;
                }
            case 1:
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, (short)npcid, "Con đã vào lớp từ trước rồi mà.");
                } else if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, (short)npcid, "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                } else if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, (short)npcid, "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                } else {
                    if (b3 == 0) {
                        Admission.Admission(p,(byte)5);
                    } else if (b3 == 1) {
                        Admission.Admission(p,(byte)6);
                    }

                    Service.chatNPC(p, (short)npcid, "Hãy chăm chỉ luyện tập, có làm thì mới có ăn con nhé.");
                }
                break;
            case 2:
                if (p.c.get().nclass != 5 && p.c.get().nclass != 6) {
                    Service.chatNPC(p, (short)npcid, "Con không phải học sinh của trường này, ta không thể giúp con tẩy điểm dược rồi.");
                } else if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, (short)npcid, "Số lần tẩy điểm tiềm năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, (short)npcid, "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                } else if (b3 == 1) {
                    if (p.c.get().countTayKyNang < 1) {
                        Service.chatNPC(p, (short)npcid, "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }
                    p.restSpoint();
                    --p.c.get().countTayKyNang;
                    Service.chatNPC(p, (short)npcid, "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                }
                break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Ngươi là người thổi tan băng của trường Ookaza và mang kem về cho trường Hirosaki đúng không?");
                break;
            case 4:
                Service.chatNPC(p, (short)npcid, "Ta đang hơi mệt xíu, ta sẽ giao chiến với con sau nha! Bye bye...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTajima(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Chào mừng con đến với ngôi làng đi đâu cũng phải nhớ về!");
                break;
            case 1:
                Service.chatNPC(p, (short)npcid, "Chức năng Huỷ vật phẩm và nhiệm vụ đang cập nhật!");
                break;
            case 2:
                if (p.c.timeRemoveClone > System.currentTimeMillis()) {
                    p.toNhanBan();
                } else {
                    Service.chatNPC(p, (short)npcid, "Con không có phân thân để sử dụng chức năng này!");
                }
                break;
            case 3:
                if (!p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, "Con không phải phân thân để sử dụng chức năng này!");
                    return;
                }
                if (!p.c.clone.isDie && p.c.timeRemoveClone > System.currentTimeMillis()) {
                    p.exitNhanBan(true);
                }
                break;
            case 4:
            case 5:
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcRei(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ngươi đến đây làm gì, không có nhiệm vụ cho ngươi đâu!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcKirin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ngươi đến đây làm gì, không có nhiệm vụ cho ngươi đâu!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcSoba(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Ta sẽ sớm có nhiệm vụ cho con!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcSunoo(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Service.chatNPC(p, (short)npcid, "Khụ khụ...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcGuriin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcMatsurugi(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

     public static void npcOkanechan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                Server.manager.sendTB(p, "Hướng dẫn", "- Để nạp tiền hoặc mua đồ, con hãy lên Website hoặc THAM GIA BOX ZALO của game để nạp nhé!");
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                switch(b3) {
                    case 0:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 10 && p.c.checkLevel[0] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(223, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }

                            p.c.checkLevel[0] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 1:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 20 && p.c.checkLevel[1] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(224, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[1] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 2:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 30 && p.c.checkLevel[2] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(225, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[2] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    case 3:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 40 && p.c.checkLevel[3] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(226, true));
                            if(p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[3] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 4:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 50 && p.c.checkLevel[4] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(227, true));
                            if(p.status == 1) {
                                p.upluongMessage(1500L);
                                p.c.luongTN += 1500;
                            } else {
                                p.upluongMessage(3000L);
                            }
                            p.c.checkLevel[4] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 5:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.level >= 70 && p.c.checkLevel[5] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(228, true));
                            if(p.status == 1) {
                                p.upluongMessage(1500L);
                                p.c.luongTN += 1500;
                            } else {
                                p.upluongMessage(3000L);
                            }
                            p.c.checkLevel[5] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 6:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level >= 90 && p.c.checkLevel[6] == 0) {
                            if(p.status == 1) {
                                p.upluongMessage(2500L);
                                p.c.luongTN += 2500;
                            } else {
                                p.upluongMessage(5000L);
                            }
                            p.c.checkLevel[6] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 7:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, "Chức năng này không dành cho phân thân!");
                            return;
                        }

                        if (p.c.level >= 110 && p.c.checkLevel[7] == 0) {
                            if(p.status == 1) {
                                p.upluongMessage(2500L);
                                p.c.luongTN += 2500;
                            } else {
                                p.upluongMessage(5000L);
                            }
                            p.c.checkLevel[7] = 1;
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    case 8:
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level >= 130 && p.c.checkLevel[8] == 0) {
                            if(p.status == 1) {
                                p.upluongMessage(3500L);
                                p.c.luongTN += 3500;
                            } else {
                                p.upluongMessage(7000L);
                            }
                            p.c.checkLevel[8] = 1;
                            Service.chatNPC(p, (short)npcid, "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, (short)npcid, "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }

                        return;
                    default: {
                        break;
                    }
                }
                break;
              case 2: {
                switch (b3) {
                    case 0:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.yen < 1000000000) {
                            Service.chatNPC(p, (short) npcid, "Mày cần phải có trên 1.000.000.000 yên mới đổi được");
                        } else {
                            p.c.upyenMessage(-1000000000);
                            p.luongMessage(2000);
                            Service.chatNPC(p, (short) npcid, "Đổi yên sang lượng thành công");
                        }
                    }
                    break;
                    case 1:{    
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.yen < 1000000000) {
                            Service.chatNPC(p, (short) npcid, "Mày cần phải có trên 1.000.000.000 yên mới đổi được");
                        } else {
                            p.c.upyenMessage(-1000000000);
                            p.c.upxuMessage(50000000);
                            Service.chatNPC(p, (short) npcid, "Đổi yên sang xu thành công");
                        }
                    }
                    break;
                    case 2:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.luong < 20000) {
                            Service.chatNPC(p, (short) npcid, "Mày cần phải có trên 20.000 mới đổi được");
                        } else {
                            p.luongMessage(-20000);
                            p.c.upxuMessage(700000000);
                            Service.chatNPC(p, (short) npcid, "Đổi lượng sang xu thành công");
                        }
                    }
                    break;
                }
            }
            break;
            case 3:
                Service.chatNPC(p, (short)npcid, "Hãy rèn luyện thật chăm chỉ rồi quay lại chỗ ta nhận thưởng nha!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
     }
     public static void npcCLXTXu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 46_0_0, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 46_0_1, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckTXXu check: CheckTXXu.checkTXXuArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 45_1_0, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 45_1_1, "Nhập số lượng đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckCLLuong check: CheckCLLuong.checkCLLuongArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time +".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
public static void doimk(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
             case 0:{
                p.passold = "";
                Service.sendInputDialog(p, (short) 10, "Nhập mật khẩu cũ");
                break;
            }
             case 1: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu < 200000000 ) {
                            Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ 200tr xu");
                            break;
                        }
                            if (p.c.getBagNull() == 0) {
                            p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                            break;
                        }
                            final Item it = ItemTemplate.itemDefault(247);
                            Manager.chatKTG("Chúc Mừng người chơi " + p.c.name +  " Đã Đổi Thành Công 1 Thỏi Bạc");
                            p.c.addItemBag(false, it);
                            p.c.upxuMessage(-200000000);
                        break;
                    }
                    case 1: {
                        if (p.c.xu < 2000000000 ) {
                            Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ 2 tỷ xu");
                            break;
                            } else {
                            Manager.chatKTG("Chúc Mừng người chơi " + p.c.name +  " Đã Đổi Thành Công 1 lúc 10 Thỏi Bạc Thật Dubai");
                            p.c.upxuMessage(-2000000000);
                            Item it = new Item();
                            it.id = 247;
                            it.quantity = 10;
                            it.isLock = false;
                            p.c.addItemBag(false, it);
                            
                            for (byte i = 0; i < 20; i++) {
                                
                            break;
                        }
                    }
                }
                    case 2: {
                        if (p.c.quantityItemyTotal(247) < 1) {
                            Service.chatNPC(p, (short) npcid, "Con Cần có 1 Thỏi bạc");
                            break;
                        }
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Đã đổi 1 thỏi bạc ra 200tr xu");
                                p.c.removeItemBags(247, 1);
                            p.c.upxuMessage(200000000);
                                break;
                            }
                    }
                    case 3: {
                        if (p.c.quantityItemyTotal(247) < 20) {
                            Service.chatNPC(p, (short) npcid, "Con Cần có 20 Thỏi bạc");
                            break;
                            } else {
                            Service.chatNPC(p, (short) npcid, "Đã Gộp Thành Công Chúc Con Chơi Game Vui Vẻ");
                            p.c.removeItemBags(247, 20);
                            Item it = new Item();
                            it.id = 247;
                            it.quantity = 20;
                            it.isLock = false;
                            p.c.addItemBag(false, it);
                            
                            for (byte i = 0; i < 20; i++) {
                                
                            break;
                        }
                    }
                }                           
          }
         }  
             case 2: {
                            Server.manager.sendTB(p, "BẢNG GIÁ QUY ĐỔI", "-Bạn có thể đổi\n" + 
                                    "-  Đôi 100k coin = 20k momo .\n" +
                                    "- Đôi 100k lượng = 10k momo.\n" +
                                    "- ĐIỂM DANH HÀNG NGÀY.\n" +
                                    "- ÚP LÀNG CỔ.\n" +     
                                    "- CHƠI CÁC GAME GIẢI TRÍ\n");
                            break;
                        }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }
    public static void npcRikudou(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        MapTemplate map;
        MobTemplate mob;
        switch(menuId) {
            case 0: {
                Service.chatNPC(p, (short)npcid, "Hãy chăm chỉ lên nha.");
                break;
            }
            case 1: {
                switch(b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level < 10) {
                            Service.chatNPC(p, (short)npcid, "Con cần đạt cấp độ 10 để có thể nhận nhiệm vụ.");
                            return;
                        }

                        if (p.c.isTaskHangNgay != 0) {
                            Service.chatNPC(p, (short)npcid, "Ta đã giao nhiệm vụ cho con trước đó rồi");
                            return;
                        }

                        if (p.c.countTaskHangNgay >= 20) {
                            Service.chatNPC(p, (short)npcid, "Con đã hoàn thành hết nhiệm vụ ngày hôm nay rồi, ngày mai hãy quay lại nha.");
                            return;
                        }

                        mob = Service.getMobIdByLevel(p.c.level);
                        if (mob != null) {
                            map = Service.getMobMapId(mob.id);
                            if (map != null) {
                                p.c.taskHangNgay[0] = 0;
                                p.c.taskHangNgay[1] = 0;
                                p.c.taskHangNgay[2] = (int)Util.nextInt(10, 25);
                                p.c.taskHangNgay[3] = mob.id;
                                p.c.taskHangNgay[4] = map.id;
                                p.c.isTaskHangNgay = 1;
                                p.c.countTaskHangNgay++;
                                Service.getTaskOrder(p.c, (byte)0);
                                Service.chatNPC(p, (short)npcid, "Đây là nhiệm vụ thứ " + p.c.countTaskHangNgay + "/20 trong ngày của con.");
                            }
                        }
                        break;
                    }

                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskHangNgay == 0) {
                            Service.chatNPC(p, (short)npcid, "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }

                        p.c.isTaskHangNgay = 0;
                        p.c.countTaskHangNgay--;
                        p.c.taskHangNgay = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskHangNgay};
                        Service.clearTaskOrder(p.c, (byte)0);
                        Service.chatNPC(p, (short)npcid, "Con đã huỷ nhiệm vụ lần này.");
                        break;
                    }

                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskHangNgay == 0) {
                            Service.chatNPC(p, (short)npcid, "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }

                        if (p.c.getBagNull() == 0) {
                            p.conn.sendMessageLog(Language.NOT_ENOUGH_BAG);
                            return;
                        }

                        if (p.c.taskHangNgay[1] < p.c.taskHangNgay[2]) {
                            Service.chatNPC(p, (short)npcid, "Con chưa hoàn thành nhiệm vụ ta giao!");
                            return;
                        }

                        p.c.isTaskHangNgay = 0;
                        p.c.taskHangNgay = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskHangNgay};
                        Service.clearTaskOrder(p.c, (byte)0);
                        long luongUp = Util.nextInt(500, 1000);
                        if(p.c.vip < 1){
                            p.c.upxuMessage(10000L);
                            p.upluongMessage(luongUp/2);
                        }else {
                            p.c.upxuMessage(100000L);
                            p.upluongMessage(1000L);
                        }
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 2;
                        }
                        if(p.c.countTaskHangNgay % 2 == 0) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 733 : 760, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = (int)Util.nextInt(2,3);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 734 : 761, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = (int)Util.nextInt(2,3);
                            p.c.addItemBag(true, itemUp);
                        }

                        Service.chatNPC(p, (short)npcid, "Con hãy nhận lấy phần thưởng của mình.");
                        break;
                    }

                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.taskHangNgay[4] != -1) {
                            Map ma = Manager.getMapid(p.c.taskHangNgay[4]);
                            int var8;
                            TileMap area;
                            for(var8 = 0; var8 < ma.area.length; ++var8) {
                                area = ma.area[var8];
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                        }
                        Service.chatNPC(p, (short)npcid, "Con chưa nhận nhiệm vụ nào cả!");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch(b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.level < 30) {
                            Service.chatNPC(p, (short)npcid, "Con cần đạt cấp độ 30 để có thể nhận nhiệm vụ tà thú.");
                            return;
                        }

                        if (p.c.isTaskTaThu != 0) {
                            Service.chatNPC(p, (short)npcid, "Ta đã giao nhiệm vụ cho con trước đó rồi");
                            return;
                        }

                        if (p.c.countTaskTaThu >= 2) {
                            Service.chatNPC(p, (short)npcid, "Con đã hoàn thành hết nhiệm vụ ngày hôm nay rồi, ngày mai hãy quay lại nha.");
                            return;
                        }
                        mob = Service.getMobIdTaThu(p.c.level);
                        if (mob != null) {
                            map = Service.getMobMapIdTaThu(mob.id);
                            if (map != null) {
                                p.c.taskTaThu[0] = 1;
                                p.c.taskTaThu[1] = 0;
                                p.c.taskTaThu[2] = 1;
                                p.c.taskTaThu[3] = mob.id;
                                p.c.taskTaThu[4] = map.id;
                                p.c.isTaskTaThu = 1;
                                ++p.c.countTaskTaThu;
                                Service.getTaskOrder(p.c, (byte)1);
                                Service.chatNPC(p, (short)npcid, "Hãy hoàn thành nhiệm vụ và trở về đây nhận thưởng.");
                            }
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskTaThu == 0) {
                            Service.chatNPC(p, (short)npcid, "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }

                        Service.clearTaskOrder(p.c, (byte)1);
                        p.c.isTaskTaThu = 0;
                        --p.c.countTaskTaThu;
                        p.c.taskTaThu = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskTaThu};
                        Service.chatNPC(p, (short)npcid, "Con đã huỷ nhiệm vụ lần này.");
                        break;
                    }

                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isTaskTaThu == 0) {
                            Service.chatNPC(p, (short)npcid, "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }

                        if (p.c.taskTaThu[1] < p.c.taskTaThu[2]) {
                            Service.chatNPC(p, (short)npcid, "Con chưa hoàn thành nhiệm vụ ta giao!");
                            return;
                        }

                        if (p.c.getBagNull() < 2) {
                            Service.chatNPC(p, (short)npcid, "Hành trang của con không đủ chỗ trống để nhận thưởng");
                            return;
                        }

                        p.c.isTaskTaThu = 0;
                        p.c.taskTaThu = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskTaThu};
                        Service.clearTaskOrder(p.c, (byte)1);
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 3;
                        }
                        Item item = ItemTemplate.itemDefault(251, false);
                        item.quantity = (int)Util.nextInt(3, 4);
                        item.isLock = false;
                        p.c.addItemBag(true, item);

                        if(p.c.countTaskTaThu == 1) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 737 : 764, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = (int)Util.nextInt(20,30);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 738 : 765, true);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = (int)Util.nextInt(20,30);
                            p.c.addItemBag(true, itemUp);
                        }

                        Service.chatNPC(p, (short)npcid, "Con hãy nhận lấy phần thưởng của mình.");
                        break;
                    }

                    default: {
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(ChienTruong.chienTruong == null) {
                            Service.chatNPC(p, (short)npcid, "Chiến trường chưa được tổ chức.");
                            return;
                        }
                        if(ChienTruong.chienTruong != null) {
                            if(ChienTruong.chienTruong30 && (p.c.level < 30 || p.c.level >= 50)) {
                                Service.chatNPC(p, (short)npcid, "Bây giờ là thời gian chiến trường cho cấp độ từ 30 đến 49. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }else if(ChienTruong.chienTruong50 && p.c.level < 50) {
                                Service.chatNPC(p, (short)npcid, "Bây giờ là thời gian chiến trường cho cấp độ lớn hơn hoặc bằng 50. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }
                            if((ChienTruong.chienTruong30 ||ChienTruong.chienTruong50) && p.c.pheCT == 1) {
                                Service.chatNPC(p, (short)npcid, "Con đã điểm danh phe Hắc giả trước đó rồi.");
                                return;
                            }
                            if(ChienTruong.start && p.c.pheCT==-1) {
                                Service.chatNPC(p, (short)npcid, "Chiến trường đã bắt đầu, không thể báo danh.");
                                return;
                            }
                            if((ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == -1 ) {
                                if (p.c.pointUydanh < 5000) {
                                    p.c.pointUydanh += 10;
                                }
                                p.c.pheCT = 0;
                                p.c.pointCT = 0;
                                p.c.isTakePoint = 0;
                                p.c.typepk = 4;
                                Service.ChangTypePkId(p.c, (byte)4);
                                Service.updatePointCT(p.c, 0);
                                if(p.c.party != null) {
                                    p.c.party.removePlayer(p.c.id);
                                }
                                if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                    ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                                } else {
                                    ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                                }
                                Map ma = Manager.getMapid(ChienTruong.chienTruong.map[0].id);
                                for (TileMap area : ma.area) {
                                    if (area.numplayers < ma.template.maxplayers) {
                                        p.c.tileMap.leave(p);
                                        area.EnterMap0(p.c);
                                        return;
                                    }
                                }
                                return;
                            }
                            p.c.typepk = 4;
                            Service.ChangTypePkId(p.c, (byte)4);
                            Service.updatePointCT(p.c, 0);
                            if(p.c.party != null) {
                                p.c.party.removePlayer(p.c.id);
                            }
                            if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                            } else {
                                ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                            }
                            Map ma = Manager.getMapid(ChienTruong.chienTruong.map[0].id);
                            for (TileMap area : ma.area) {
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(ChienTruong.chienTruong == null) {
                            Service.chatNPC(p, (short)npcid, "Chiến trường chưa được tổ chức.");
                            return;
                        }
                        if(ChienTruong.chienTruong != null) {
                            if( ChienTruong.chienTruong30 && (p.c.level < 30 || p.c.level >= 50)) {
                                Service.chatNPC(p, (short)npcid, "Bây giờ là thời gian chiến trường cho cấp độ từ 30 đến 49. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }else if(ChienTruong.chienTruong50 && p.c.level < 50) {
                                Service.chatNPC(p, (short)npcid, "Bây giờ là thời gian chiến trường cho cấp độ lớn hơn hoặc bằng 50. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }
                            if(ChienTruong.start && p.c.pheCT==-1) {
                                Service.chatNPC(p, (short)npcid, "Chiến trường đã bắt đầu, không thể báo danh.");
                                return;
                            }
                            if((ChienTruong.chienTruong30 ||ChienTruong.chienTruong50) && p.c.pheCT == 0) {
                                Service.chatNPC(p, (short)npcid, "Con đã điểm danh phe Bạch giả trước đó rồi.");
                                return;
                            }
                            if( (ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == -1 ) {
                                if (p.c.pointUydanh < 5000) {
                                    p.c.pointUydanh += 10;
                                }
                                p.c.pheCT = 1;
                                p.c.pointCT = 0;
                                p.c.typepk = 5;
                                p.c.isTakePoint = 0;
                                Service.ChangTypePkId(p.c, (byte)5);
                                Service.updatePointCT(p.c, 0);
                                if(p.c.party != null) {
                                    p.c.party.removePlayer(p.c.id);
                                }
                                if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                    ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                                } else {
                                    ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                                }
                                Map ma = Manager.getMapid(ChienTruong.chienTruong.map[6].id);
                                for (TileMap area : ma.area) {
                                    if (area.numplayers < ma.template.maxplayers) {
                                        p.c.tileMap.leave(p);
                                        area.EnterMap0(p.c);
                                        return;
                                    }
                                }
                                return;
                            }
                            p.c.typepk = 5;
                            Service.ChangTypePkId(p.c, (byte)5);
                            Service.updatePointCT(p.c, 0);
                            if(p.c.party != null) {
                                p.c.party.removePlayer(p.c.id);
                            }
                            if(!ChienTruong.bxhCT.containsKey(p.c)) {
                                ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                            } else {
                                ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                            }
                            Map ma = Manager.getMapid(ChienTruong.chienTruong.map[6].id);
                            for (TileMap area : ma.area) {
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    case 2: {
                        if(ChienTruong.finish) {
                            Service.evaluateCT(p.c);
                        } else {
                            Server.manager.sendTB(p, "Kết quả", "Chưa có thông tin.");
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "Chiến trường được mở 2 lần mỗi ngày.\n" +
                        "- Chiến trường lv30: giành cho nhân vật level từ 30 đến 45, điểm danh vào lúc 19h và bắt đầu từ 19h30' đến 20h30'.\n" +
                        "- Chiến trường lv50: giành cho nhân vật level từ 50 trở lên, điểm danh vào lúc 21h và bắt đầu từ 21h30' đến 22h30'.\n\n" +
                        "+ Top1: 10v đan mỗi loại + 3tr xu.\n" +
                        "+ Top 2: 7v đan mỗi loại + 2tr xu.\n" +
                        "+ Top 3: 5v đan mỗi loại + 1tr xu.\n" +
                        "+ Phe thắng: 1v đan mỗi loại + 500k xu.");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcGoosho(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                p.requestItem(14);
                break;
            case 1:
                p.requestItem(15);
                break;
            case 2:
                p.requestItem(32);
                break;
            case 3:
                p.requestItem(34);
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTruCoQuan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if(p.c.quantityItemyTotal(260) < 1) {
                    p.sendAddchatYellow("Không có chìa khoá để mở cửa này.");
                    return;
                }
                if(p.c.tileMap.map.lanhDiaGiaToc != null && p.c.tileMap.map.mapLDGT()) {
                    switch (p.c.tileMap.map.id) {
                        case 80: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(1, p);
                            break;
                        }
                        case 81: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(2, p);
                            break;
                        }
                        case 82: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(3, p);
                            break;
                        }
                        case 83: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(4, p);
                            break;
                        }
                        case 84: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(5, p);
                            break;
                        }
                        case 85: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(6, p);
                            break;
                        }
                        case 86: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(7, p);
                            break;
                        }
                        case 87: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(8, p);
                            Server.manager.sendTB(p, "Ghi chú", "Con đường này sẽ dẫn đến cánh cửa nơi ở của một nhân vật huyền bí đã bị lời nguyền cổ " +
                                    "xưa yểm bùa rằng sẽ không ai có thể đánh bại được nhân vật huyền bí này. Bạn hãy mau tìm cách hoá giải lời nguyền.");
                            break;
                        }
                        case 88: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(9, p);
                            Server.manager.sendTB(p, "Ghi chú", "Con đường này sẽ dẫn đến cánh cửa nơi ở của một nhân vật huyền bí đã bị lời nguyền cổ " +
                                    "xưa yểm bùa rằng sẽ không ai có thể đánh bại được nhân vật huyền bí này. Bạn hãy mau tìm cách hoá giải lời nguyền.");
                            break;
                        }
                        case 89: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(10, p);
                            Server.manager.sendTB(p, "Ghi chú", "Con đường này sẽ dẫn đến cánh cửa nơi ở của một nhân vật huyền bí đã bị lời nguyền cổ " +
                                    "xưa yểm bùa rằng sẽ không ai có thể đánh bại được nhân vật huyền bí này. Bạn hãy mau tìm cách hoá giải lời nguyền.");
                            break;
                        }
                        default: {
                            break;
                        }

                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcShinwa(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.menuIdAuction = b3;
                final List<ShinwaTemplate> itemShinwas = ShinwaManager.entrys.get((int)b3);
                final Message mess = new Message(103);
                mess.writer().writeByte(b3);
                if(itemShinwas != null) {
                    mess.writer().writeInt(itemShinwas.size());
                    ShinwaTemplate item;
                    for(int i = 0; i < itemShinwas.size(); i++) {
                        item = itemShinwas.get(i);
                        if(item != null) {
                            mess.writer().writeInt(i);
                            mess.writer().writeInt(item.getRemainTime());
                            mess.writer().writeShort(item.getItem().quantity);
                            mess.writer().writeUTF(item.getSeller());
                            mess.writer().writeInt((int)item.getPrice());
                            mess.writer().writeShort(item.getItem().id);
                        } else {
                            mess.writer().writeInt(i);
                            mess.writer().writeInt(-1);
                            mess.writer().writeShort(0);
                            mess.writer().writeUTF("");
                            mess.writer().writeInt(999999999);
                            mess.writer().writeShort(12);
                        }
                    }
                } else {
                    mess.writer().writeInt(0);
                }
                mess.writer().flush();
                p.conn.sendMessage(mess);
                mess.cleanup();
                break;
            }
            case 1: {
                final int itemShinwa = ShinwaManager.entrys.size();
                System.out.println("Số lượng "+ itemShinwa);
                if(itemShinwa > 0){
                    p.conn.sendMessageLog("Gian hàng đã full vật phẩm");
                    break;
                }
                p.menuIdAuction = -2;
                p.requestItem(36);
                break;
            }
            case 2: {
                try {
                    synchronized (ShinwaManager.entrys.get((int)-1)) {
                        List<ShinwaTemplate> itemShinwas = ShinwaManager.entrys.get((int)-1);
                        System.out.print(itemShinwas.size());
                        List<Integer> ind = new ArrayList<>();
                        ShinwaTemplate item;
                        for(int i = itemShinwas.size() - 1; i>=0; i--) {
                            item = itemShinwas.get(i);
                            if(item != null && item.getSeller().equals(p.c.name)) {
                                if(p.c.getBagNull() == 0) {
                                    Service.chatNPC(p, (short) npcid, "Hành trang không đủ chỗ trống để nhận thêm vật phẩm!");
                                    break;
                                } 
                                p.c.addItemBag(true, item.getItem());
                                ind.add(i);
                                itemShinwas.remove(i);
                            }
                        }                       
                        if(ind.size() < 1) {
                            Service.chatNPC(p, (short) npcid, "Con không có đồ để nhận lại!");
                            return;
                        }
                        for(int i : ind) {
                            itemShinwas.remove(i);
                        }
                    }
                } catch (Exception e) {
                    p.conn.sendMessageLog("Có lỗi, vui lòng thử lại sau!");
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcChiHang(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcRakkii(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.requestItem(38);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                Service.sendInputDialog(p, (short) 4, "Nhập Gift Code tại đây");
                break;
            }
            case 2: {
                switch (b3) {
                    case 0:
                    case 1: {
                        Server.manager.rotationluck[0].luckMessage(p);
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Vòng xoay vip", "Hãy đặt cược xu và thử vận may của mình trong 2 phút nha.");
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            case 3: {
                switch (b3) {
                    case 0:
                    case 1: {
                        Server.manager.rotationluck[1].luckMessage(p);
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Vòng xoay thường", "Hãy đặt cược xu và thử vận may của mình trong 2 phút nha.");
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcLongDen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcKagai(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.clan == null) {
                            Service.chatNPC(p, (short) npcid, "Con chưa có Gia tộc.");
                            return;
                        }
                        if (p.c.clan != null && p.c.clan.typeclan != 4) {
                            Service.chatNPC(p, (short) npcid, "Con không phải tộc trưởng, không thể mời gia tộc chiến.");
                            return;
                        }
                        Service.sendInputDialog(p, (short) 5, "Nhập tên gia tộc đối phương");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                } else {
                    Item it;
                    Char var6;
                    switch (b3) {
                        case 0:
                            if (p.c.pointUydanh < 3000000) {
                                Service.chatNPC(p, (short) npcid, "Chức năng tạm đóng");
                                return;
                            } else {
                                if (p.c.getBagNull() < 1) {
                                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                } else {
                                    var6 = p.c;
                                    var6.pointUydanh -= 300;
                                    it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                    it.isLock = false;
                                    it.quantity = 1;
                                    it.isExpires = true;
                                    it.expires = System.currentTimeMillis() + 259200000L;
                                    p.c.addItemBag(false, it);
                                    p.c.upxuMessage(3000000);
                                }

                                return;
                            }
                        case 1: {
                            if (p.c.pointUydanh < 7099990) {
                                Service.chatNPC(p, (short) npcid, "Chức năng tạm đóng");
                                return;
                            } else {
                                if (p.c.getBagNull() < 1) {
                                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                } else {
                                    var6 = p.c;
                                    var6.pointUydanh -= 700;
                                    it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                    it.isLock = false;
                                    it.quantity = 1;
                                    it.isExpires = true;
                                    it.expires = System.currentTimeMillis() + 432000000L;
                                    p.c.addItemBag(false, it);
                                    p.c.upxuMessage(5000000);
                                }
                                return;
                            }

                        }
                        case 2: {
                            if (p.c.pointUydanh < 200000000) {
                                Service.chatNPC(p, (short) npcid, "Chức năng tạm đóng");
                            } else if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                            } else {
                                var6 = p.c;
                                var6.pointUydanh -= 2000;
                                it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                it.isLock = false;
                                it.quantity = 1;
                                it.isExpires = true;
                                it.expires = System.currentTimeMillis() + 1296000000L;
                                p.c.addItemBag(false, it);
                                p.c.upxuMessage(10000000);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    break;
                } else {
                    switch (b3) {
                        case 0: {
                            p.requestItem(43);
                            break;
                        }
                        case 1: {
                            p.requestItem(44);
                            break;
                        }
                        case 2: {
                            p.requestItem(45);
                            break;
                        }
                        case 3: {
                            Server.manager.sendTB(p, "Hướng dẫn", "- Tinh luyện...");
                            break;
                        }
                        default: {
                            Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                            break;
                        }
                    }
                }
                break;
            }
            case 0:
            case 2:
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTienNu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        if (p.typemenu == 33) {
            Item it;
            switch(Server.manager.event) {
                //Hè
                case 1: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    } else {
                        switch (menuId) {
                            case 0: {
                                if (p.c.quantityItemyTotal(432) >= 1 && p.c.quantityItemyTotal(428) >= 3 && p.c.quantityItemyTotal(429) >= 2 && p.c.quantityItemyTotal(430) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(434);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(432, 1);
                                        p.c.removeItemBags(428, 3);
                                        p.c.removeItemBags(429, 2);
                                        p.c.removeItemBags(430, 3);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                            case 1: {
                                if (p.c.quantityItemyTotal(433) >= 1 && p.c.quantityItemyTotal(428) >= 2 && p.c.quantityItemyTotal(429) >= 3 && p.c.quantityItemyTotal(431) >= 2) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(435);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(433, 1);
                                        p.c.removeItemBags(428, 2);
                                        p.c.removeItemBags(429, 3);
                                        p.c.removeItemBags(431, 2);
                                    }
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                //Trung thu
                case 2: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    } else {
                        switch (menuId) {
                            case 0: {
                                if (p.c.quantityItemyTotal(304) >= 1 && p.c.quantityItemyTotal(298) >= 1 && p.c.quantityItemyTotal(299) >= 1 && p.c.quantityItemyTotal(300) >= 1 && p.c.quantityItemyTotal(301) >= 1) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(302);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(304, 1);
                                        p.c.removeItemBags(298, 1);
                                        p.c.removeItemBags(299, 1);
                                        p.c.removeItemBags(300, 1);
                                        p.c.removeItemBags(301, 1);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                            case 1: {
                                if (p.c.quantityItemyTotal(305) >= 1 && p.c.quantityItemyTotal(298) >= 1 && p.c.quantityItemyTotal(299) >= 1 && p.c.quantityItemyTotal(300) >= 1 && p.c.quantityItemyTotal(301) >= 1) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(303);
                                        p.c.addItemBag(true, it);
                                        p.c.removeItemBags(305, 1);
                                        p.c.removeItemBags(298, 1);
                                        p.c.removeItemBags(299, 1);
                                        p.c.removeItemBags(300, 1);
                                        p.c.removeItemBags(301, 1);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                            case 2: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 3 && p.c.quantityItemyTotal(293) >= 2 && p.c.quantityItemyTotal(294) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(298);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 3);
                                        p.c.removeItemBags(293, 2);
                                        p.c.removeItemBags(294, 3);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                            case 3: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(295) >= 3 && p.c.quantityItemyTotal(294) >= 2) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(299);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 2);
                                        p.c.removeItemBags(295, 3);
                                        p.c.removeItemBags(294, 2);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                            case 4: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(295) >= 3 && p.c.quantityItemyTotal(297) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(300);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 2);
                                        p.c.removeItemBags(295, 3);
                                        p.c.removeItemBags(297, 3);
                                    }

                                    return;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                            case 5: {
                                if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(296) >= 2 && p.c.quantityItemyTotal(297) >= 3) {
                                    if (p.c.getBagNull() == 0) {
                                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    } else {
                                        it = ItemTemplate.itemDefault(301);
                                        p.c.addItemBag(true, it);
                                        p.c.upyenMessage(-10000L);
                                        p.c.removeItemBags(292, 2);
                                        p.c.removeItemBags(296, 2);
                                        p.c.removeItemBags(297, 3);
                                    }
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Hành trang của con không có đủ nguyên liệu");
                                }
                                break;
                            }
                        }
                    }
                    break;
                }

                //Noel
                case 3: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 6, "Nhập số lượng bánh Chocolate muốn làm.");
                            break;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 7, "Nhập số lượng bánh  Dâu tây muốn làm.");
                            break;
                        }
                        case 2: {
                            if(p.c.pointNoel < 3500) {
                                Service.chatNPC(p, (short) npcid, "Con cần ít nhất 3500 điểm để đổi mặt nạ 7 ngày.");
                                return;
                            }
                            p.c.pointNoel -= 3500;
                            it = ItemTemplate.itemDefault(p.c.gender == 1 ? 407 : 408);
                            it.isLock = false;
                            it.quantity = 1;
                            it.isExpires = true;
                            it.expires = System.currentTimeMillis() + 604800000L;
                            p.c.addItemBag(false, it);
                            break;
                        }
                        case 3: {
                            if(p.c.pointNoel < 5000) {
                                Service.chatNPC(p, (short) npcid, "Con cần ít nhất 5000 điểm để đổi pet Hoả long 7 ngày.");
                                return;
                            }
                            p.c.pointNoel -= 5000;
                            it = ItemTemplate.itemDefault(583);
                            it.isLock = false;
                            it.quantity = 1;
                            it.isExpires = true;
                            it.expires = System.currentTimeMillis() + 604800000L;
                            p.c.addItemBag(false, it);
                            break;
                        }
                        case 4: {
                            String a = "";
                            if(Rank.bxhBossTuanLoc.isEmpty()) {
                                a = "Chưa có thông tin.";
                            }
                            for(Rank.Entry3 item : Rank.bxhBossTuanLoc) {
                                a += item.index +". "+item.name+": "+item.point+" điểm\n";
                            }
                            Server.manager.sendTB(p, "BXH Diệt Boss", a);
                            break;
                        }
                        case 5: {
                            Server.manager.sendTB(p, "Hướng dẫn", "- Số điểm hiện tại của bạn là: "+p.c.pointNoel+"\n" +
                                    "- Kiểm điểm sự kiện bằng cách nhận quà hàng ngày tại Cây thông (+1 điểm), trang trí cây thông (+10 điểm), giết boss Tuần lộc (+1 điểm).\n" +
                                    "- Dùng điểm để dổi lấy vật phẩm quý giá: Mặt nạ Super Broly/Onna Bugeisha 7 ngày (3500 điểm), Pet Hoả long 7 ngày (5000 điểm).\n" +
                                    "- Bánh Chocolate: 2 Bơ + 2 Kem + 3 Đường + 1 Chocolate + 5000 yên.\n" +
                                    "- Bánh Dâu tây: 3 Bơ + 3 Kem + 4 Đường + 1 Dâu tây + 10000 yên.\n");
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                //Tết
                case 4: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 110, "Nhập số lượng đan cao cấp muốn luyện :");
                            break;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 111, "Nhập số lượng đan thường muốn luyện :");
                            break;
                        }
                        
                        case 2: {
                            Server.manager.sendTB(p, "Hướng dẫn", "----------------- Luyện Đan cao cấp -----------------\n +, 3 riềng + 5 xả + 1 linh chi đỏ + 3  mắm tôm + 2 ớt + 50.000 xu + 50.000 yên.\n" +
                                    "----------------- luyện đan thường -----------------\n +,  2 riềng + 4 xả + 2 mắm tôm + 4 ớt + 40.000 xu + 40.000 yên.\n" +
                                    "----------------- đan thường  -----------------\n +, ăn vào sẽ nhận ngẫu nhiên vật phẩm nếu may mắn sẽ có đan dược nhục thân.\n" +
                                    "----------------- đan cao cấp -----------------\n +,ăn vào sẽ nhận ngẫu nhiên vật phẩm nếu may mắn sẽ có đan dược nhục thân và đan này sẽ đc tính đua top .\n");
                                    
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                // 8/3
                case 5: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0:
                            Service.sendInputDialog(p, (short) 119, "Nhập số hoa muốn làm");
                            break;
                        
                        case 1: 
                            Service.sendInputDialog(p, (short) 120, "Nhập số hoa muốn làm");
                            break;
                        
                        case 2: 
                            Service.sendInputDialog(p, (short) 121, "Nhập số hoa muốn làmt");
                            break;
                        
               
                        case 3: 
                            Service.sendInputDialog(p, (short) 122, "Nhập số hoa muốn làm");
                            break;
                  
                            case 4: 
                                Service.sendInputDialog(p, (short) 123, "Nhập tên nhân vật");
                            break;
                            
                            case 5: 
                                Service.sendInputDialog(p, (short) 124, "Nhập tên nhân vật");
                            break;
                             
                            case 6: 
                                Service.sendInputDialog(p, (short) 125, "Nhập tên nhân vật");
                            break;
                             
                            case 7: 
                                Service.sendInputDialog(p, (short) 118, "Nhập tên nhân vật");
                            break;
                             
                       
                           case 8: {
                                Server.manager.sendTB(p, "Hướng dẫn", "Cách ghép hoa: \n  - Bó hoa hồng đỏ = 8 Hoa hồng đỏ + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n" +
                                    " - Bó hoa hồng vàng = 8 Hoa hồng vàng + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n" +
                                    " - Bó hoa hồng xanh = 8 Hoa hồng xanh + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n" +
                                    " - Giỏ hoa = 8 Hoa hồng đỏ + 8 Hoa hồng vàng + 8 Hoa hồng xanh + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n");
                           }
                    }
                }
                default: {
                    Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                    break;
                }
            }

        }
    }

    public static void npcCayThong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.level < 40) {
                    p.conn.sendMessageLog("Nhân vật phải trên level 40 mới có thể nhận quà và trang trí.");
                    return;
                }
                if(p.c.isNhanQuaNoel < 1) {
                    p.conn.sendMessageLog("Hôm nay bạn đã nhận quà rồi.");
                    return;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống để nhận quà");
                    return;
                }
                p.c.isNhanQuaNoel = 0;
                p.c.pointNoel++;
                int random = (int)Util.nextInt(0,2);
                switch (random) {
                    case 0: {
                        int yen = (int)Util.nextInt(500000,1000000);
                        if(p.status == 1) {
                            yen /= 2;
                            p.c.yenTN += yen;
                        }
                        p.c.upyenMessage(yen);
                        p.sendAddchatYellow("Bạn nhận được " + yen + " yên.");
                        break;
                    }
                    case 1: {
                        int xu = (int)Util.nextInt(100000,300000);
                        if(p.status == 1) {
                            xu /= 2;
                            p.c.xuTN += xu;
                        }
                        p.c.upxuMessage(xu);
                        p.sendAddchatYellow("Bạn nhận được " + xu + " xu.");
                        break;
                    }
                    case 2: {
                        int luong = (int)Util.nextInt(50,150);
                        if(p.status == 1) {
                            luong /= 2;
                            p.c.luongTN += luong;
                        }
                        p.upluongMessage(luong);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog( Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.level < 40) {
                    p.conn.sendMessageLog("Nhân vật phải trên level 40 mới có thể nhận quà và trang trí.");
                    return;
                }
                if(p.c.quantityItemyTotal(673) < 1) {
                    p.conn.sendMessageLog("Bạn không có đủ Quà trang trí để trang trí cây thông Noel.");
                    return;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống để nhận quà");
                    return;
                }
                p.c.pointNoel += 10;
                p.c.removeItemBag(p.c.getIndexBagid(673, false), 1);
                Item it;
                int per = Util.nextInt(300);
                if(per<1) {
                    it = ItemTemplate.itemDefault(383);
                } else if (per >= 1 && per <= 3) {
                    it = ItemTemplate.itemDefault(775);
                } else {
                    per = Util.nextInt(UseItem.idItemCayThong.length);
                    it = ItemTemplate.itemDefault(UseItem.idItemCayThong[per]);
                }
                it.isLock = false;
                it.quantity = 1;
                p.c.addItemBag(true, it);
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcOngGiaNoen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if(Server.manager.event != 3) {
                    Service.chatNPC(p, (short) npcid, "Hiện tại không trong thời gian sự kiện Noel");
                    return;
                }
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.quantityItemyTotal(775) < 1000) {
                    Service.chatNPC(p, (short) npcid, "Bạn không có đủ 1000 hoa tuyết để đổi mặt nạ.");
                    return;
                }
                if(p.c.getBagNull() < 1) {
                    Service.chatNPC(p, (short) npcid, "Hành trang không đủ chỗ trống để nhận quà");
                    return;
                }
                p.c.removeItemBag( p.c.getIndexBagid(775, false), 1000);
                Item it = ItemTemplate.itemDefault(774);
                it.isLock = false;
                it.quantity = 1;
                it.isExpires = true;
                it.expires = System.currentTimeMillis() + 2592000000L;
                p.c.addItemBag(false, it);
                break;
            }
            case 1: {
                if(Server.manager.event != 3) {
                    Service.chatNPC(p, (short) npcid, "Hiện tại không trong thời gian diễn ra sự kiện Noel");
                    return;
                }
                Server.manager.sendTB(p, "Hướng dẫn", "- Kiếm hoa tuyết bằng cách sử dụng Bánh khúc cây chocolate, Bánh khúc cây dâu tây hoặc trang trí cây thông.\n- Dùng 1000 bông hoa tuyết để đổi lấy mặt nạ Satan với chỉ số khủng.");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcVuaHung(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                Service.sendInputDialog(p, (short) 9, "Nhập số COIN muốn đổi.");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
           /* case 0: {
                Service.sendInputDialog(p, (short) 9, "Nhập số COIN muốn đổi.");
                break;
            }
            case 1: {
                Service.sendInputDialog(p, (short) 1234, "Nhập số lượng muốn đổi ra coin :");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }*/
        }
    }

    public static void npcKanata_LoiDai(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.party != null && p.c.party.charID != p.c.id) {
                    p.c.party.removePlayer(p.c.id);
                }

                p.c.dunId = -1;
                p.c.isInDun = false;
                p.c.tileMap.leave(p);
                p.restCave();
                p.changeMap(p.c.mapKanata);
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.party != null && p.c.party.charID != p.c.id) {
                    Service.chatNPC(p, (short)npcid, "Con không phải nhóm trưởng, không thể đặt cược");
                    return;
                }

                Service.sendInputDialog(p, (short)3, "Đặt tiền cược (lớn hơn 1000 xu và chia hết cho 50)");
                break;
            case 2:
                Server.manager.sendTB(p, "Hướng dẫn", "- Mời đối thủ vào lôi đài\n\n- Đặt tiền cược (Lớn hơn 1000 xu và chia hết cho 50)\n\n- Khi cả 2 đã đặt tiền cược, và số tiền phải thống nhất bằng nhau thì trận so tài mới có thể bắt đầu.\n\n- Khi đã đặt tiền cược, nhưng thoát, mất kết nối hoặc thua cuộc, thì người chơi còn lại sẽ giành chiến thắng\n\n- Số tiền thắng sẽ nhận được sẽ bị trừ phí 5%\n\n- Nếu hết thời gian mà chưa có ai giành chiến thắng thì cuộc so tài sẽ tính hoà, và mỗi người sẽ nhận lại số tiền của mình với mức phí bị trừ 1%");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }
    
     public static void npccaydau(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.isDiemDanh == 0) {
                    if(p.status == 1) {
                        p.upluongMessage(250L);
                        p.c.luongTN += 250;
                    } else {
                        p.upluongMessage(500L);
                    }
                    p.c.isDiemDanh = 1;
                    Service.chatNPC(p, (short) npcid, "Điểm danh thành công, con nhận được 500 lượng.");
                } else {
                    Service.chatNPC(p, (short) npcid, "Hôm nay con đã điểm danh rồi, hãy quay lại vào ngày hôm sau nha!");
                }
                break;
            }
             }
        }

    public static void npcAdmin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
             
            case 0: 
                /*{if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.isQuaHangDong == 1) {
                    Service.chatNPC(p, (short) npcid, "Con đã nhận thưởng hôm nay rồi!");
                    return;
                }

                if (p.c.countHangDong >= 2) {
                    if(p.status == 1) {
                        p.upluongMessage(750L);
                        p.c.luongTN += 750;
                    } else {
                        p.upluongMessage(1500L);
                    }
                    p.c.isQuaHangDong = 1;
                    Service.chatNPC(p, (short) npcid, "Nhận quà hoàn thành hang động thành công, con nhận được 1500 lượng.");
                } else if (p.c.countHangDong < 2) {
                    Service.chatNPC(p, (short) npcid, "Con chưa hoàn thành đủ 2 lần đi hang động, hãy hoàn thành đủ 2 lần và quay lại gặp ta đã nhận thường");
                }
                break;
            }*/
            case 1: 
                {if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.getBagNull() < 7) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                    return;
                }

                if (p.c.level == 10&&(p.luong == 0 && p.c.xu == 0)) {
                    p.updateExp(Level.getMaxExp(20));
                        p.upluongMessage(3000000L);
                        p.c.upxuMessage(2500000L);
                        p.c.upyenMessage(250000000L);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(648, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(651, true));
                        Item it;
                int per = Util.nextInt(3);
                if(per==1) {
                    it = ItemTemplate.itemDefault(770,true);
                    it.quantity = 5000;
                } else if (per ==2) {
                    it = ItemTemplate.itemDefault(770,true);
                    it.quantity = 5500;
                } else {
                    it = ItemTemplate.itemDefault(770,true);
                    it.quantity = 6500;
                }
                it.isLock = true;
                p.c.addItemBag(true, it);
                        Service.chatNPC(p, (short) npcid, "Con đã nhận quà tân thủ thành công, chúc con trải nghiệm game vui vẻ.");
                    }   
                 else {
                    Service.chatNPC(p, (short) npcid, "Con đã nhận quà tân thủ trước đó rồi, không thể nhận lại lần nữa!");
                }
                break;
            }
            case 2: {
                
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.level == 10) {
                    p.conn.sendMessageLog("Không thể thực hiện thao tác này..");
                    return;
                }
                if(p.c.get().exptype == 1) {
                    p.c.get().exptype = 0;
                    p.sendAddchatYellow("Đã tắt nhận exp.");
                } else {
                    p.c.get().exptype = 1;
                    p.sendAddchatYellow("Đã bật nhận exp.");
                }
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.status == 1) {
                    Service.chatNPC(p, (short) npcid, "Tài khoản của con chưa được nâng cấp lên CHÍNH THỨC, không thể nhận lại phần thưởng.");
                    return;
                }
                switch (b3) {
                    case 0: {
                        if(p.c.yenTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con không có yên lưu trữ để nhận lại.");
                            return;
                        }
                        p.c.upyenMessage(p.c.yenTN);
                        p.c.yenTN = 0;
                        break;
                    }
                    case 1: {
                        if(p.c.xuTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con không có xu lưu trữ để nhận lại.");
                            return;
                        }
                        p.c.upxuMessage(p.c.xuTN);
                        p.c.xuTN -= 2000000000;
                        break;
                    }
                    case 2: {
                        if(p.c.luongTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con không có lượng lưu trữ để nhận lại.");
                            return;
                        }
                        p.upluongMessage(p.c.luongTN);
                        p.c.luongTN -= 1000000000;
                        break;
                    }
                    case 3: {
                        if(p.c.expTN <= 0) {
                            Service.chatNPC(p, (short) npcid, "Con không có kinh nghiệm lưu trữ để nhận lại.");
                            return;
                        }
                        p.updateExp(p.c.expTN);
                        p.c.expTN = 0;
                        break;
                    }
                    case 4: {
                        if(p.c.vip < 18){
                        Service.chatNPC(p, (short) npcid, "Yêu cầu  VIP18 mới được cất được vô hạn xu!");
                        return;
                        }else
                        if(p.c.xu < 2000000000) {
                            Service.chatNPC(p, (short) npcid, "Không đủ 2 tỏi xu để cất!");
                            return;
                        }else{
                        p.c.upxuMessage(-2000000000L);
                        p.c.xuTN += 2000000000;
                       
                    } break;}
                    case 5: {
                        if(p.c.vip < 18){
                        Service.chatNPC(p, (short) npcid, "Yêu cầu  VIP18 mới được cất được vô hạn lượng!.");
                        return;
                        }
                        if(p.luong < 1000000000) {
                            Service.chatNPC(p, (short) npcid, "Không đủ 1 tỏi lượng để cất!");
                            return;
                        }else{
                       p.upluongMessage(-1000000000L);
                        p.c.luongTN += 1000000000;
                        
                    }break;}
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                if(p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.clone == null) {
                    Service.chatNPC(p, (short) npcid, "Con không có phân thân để sử dụng chức năng này.");
                    return;
                }
                Service.startYesNoDlg(p, (byte) 5, "Sau khi lựa chọn, tất cả dữ liệu như trang bị, thú cưỡi, điểm,... của phân thân sẽ bị reset về ban đầu. Hãy chắc chắn rằng bạn đã tháo toàn bộ trang bị của phân thân và xác nhận muốn reset.");
                break;
            }
            case 5: {
                Server.manager.sendTB(p, "Hướng dẫn", "- Vừa vào chơi, hãy đến chỗ ta nhận quà tân thủ bao gồm: 100tr xu, 20k lượng, 100tr yên \n- Mỗi ngày con được điềm danh hàng ngày 1 lần và nhận 500 lượng \n- Nếu mỗi ngày hoàn thành hang động đủ 2 lần con hãy đến chỗ ta và Nhận quà hang động để nhận 1000 lượng\n\n** Lưu ý, nếu là tài khoản trải nghiệm, con chỉ có thể nhận được 1 nửa phần thường từ ta.");
                break;
            }
        }
    }

    public static void npcRikudou_ChienTruong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0: {
                p.c.typepk = 0;
                Service.ChangTypePkId(p.c, (byte)0);
                p.c.tileMap.leave(p);
                p.restCave();
                p.changeMap(p.c.mapLTD);
                break;
            }
            case 1: {
                Service.evaluateCT(p.c);
                break;
            }

            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcKagai_GTC(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (p.c.mapid) {
            case 117: {
                switch(menuId) {
                    case 0: {
                        p.c.typepk = 0;
                        Service.ChangTypePkId(p.c, (byte)0);
                        p.c.tileMap.leave(p);
                        p.restCave();
                        p.changeMap(p.c.mapLTD);
                        break;
                    }
                    case 1: {
                        Service.chatNPC(p, (short) npcid, "Đặt cược");
                        Service.sendInputDialog(p, (short)8, "Đặt tiền cược (lớn hơn 1000 xu và chia hết cho 50)");
                        break;
                    }

                    default: {
                        Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                        break;
                    }
                }
                break;
            }
            case 118:
            case 119: {
                switch(menuId) {
                    case 0: {
                        p.c.typepk = 0;
                        Service.ChangTypePkId(p.c, (byte)0);
                        p.c.tileMap.leave(p);
                        p.restCave();
                        p.changeMap(p.c.mapLTD);
                        break;
                    }
                    case 1: {
                        Service.evaluateCT(p.c);
                        break;
                    }
                    default: {
                        Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                        break;
                    }
                }
                break;
            }
        }
    }
    
       public static void npcKinhmach(Player p, byte npcid, byte menuId, byte b3) throws IOException {
           
        switch(menuId) {
                case 0: {
                    if (p.c.lvkm !=0){
                    p.conn.sendMessageLog("con đã mở kinh mạch rồi");
                    return;
                    }
                    if (p.c.expkm < 5000000){
                    p.conn.sendMessageLog("Không đủ 5 triệu EXP kinh mạch để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-100000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-100000);
                        p.c.expkm -= 5000000;
                        p.c.lvkm = 1;
                        p.c.get().potential0+= 10000;
                        p.c.get().potential1 += 10000;
                        p.c.get().potential2 += 10000;
                        p.c.get().potential3 += 10000;
                        p.conn.sendMessageLog("con đã học thành công kinh mạch hiện tại đang là lv1 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 1:{
                    if (p.c.lvkm !=1){
                    p.conn.sendMessageLog("Mở kinh mạch đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 10000000){
                    p.conn.sendMessageLog("Không đủ 10 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-200000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-200000);
                        p.c.expkm -= 10000000;
                        p.c.lvkm = 2;
                        p.c.get().potential0+= 20000;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential2 += 20000;
                        p.c.get().potential3 += 20000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv2 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 2: {
                    if (p.c.lvkm !=2){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 2 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 15000000){
                    p.conn.sendMessageLog("Không đủ 15 triệu EXP kinh mạch để nâng, hãy đi đánh cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-300000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-300000);
                        p.c.expkm -= 15000000;
                        p.c.lvkm = 3;
                        p.c.get().potential0+= 30000;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential2 += 30000;
                        p.c.get().potential3 += 30000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv3 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 3: {
                    if (p.c.lvkm !=3){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 3 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 20000000){
                    p.conn.sendMessageLog("Không đủ 20 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-400000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-400000);
                        p.c.expkm -= 20000000;
                        p.c.lvkm = 4;
                        p.c.get().potential0+= 40000;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential2 += 40000;
                        p.c.get().potential3 += 40000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv4 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 4: {
                    if (p.c.lvkm !=4){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 4 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 25000000){
                    p.conn.sendMessageLog("Không đủ 25 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-500000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-500000);
                        p.c.expkm -= 25000000;
                        p.c.lvkm = 5;
                        p.c.get().potential0+= 50000;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential2 += 50000;
                        p.c.get().potential3 += 50000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv5 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 5: {
                    if (p.c.lvkm !=5){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 5 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 30000000){
                    p.conn.sendMessageLog("Không đủ 30 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-600000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-600000);
                        p.c.expkm -= 30000000;
                        p.c.lvkm = 6;
                        p.c.get().potential0+= 60000;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential2 += 60000;
                        p.c.get().potential3 += 60000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv6 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 6: {
                    if (p.c.lvkm !=6){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 6 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 35000000){
                    p.conn.sendMessageLog("Không đủ 35 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-700000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-700000);
                        p.c.expkm -= 35000000;
                        p.c.lvkm = 7;
                        p.c.get().potential0+= 70000;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential2 += 70000;
                        p.c.get().potential3 += 70000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv7 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 7: {
                    if (p.c.lvkm !=7){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 7 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 40000000){
                    p.conn.sendMessageLog("Không đủ 40 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-800000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-800000);
                        p.c.expkm -= 40000000;
                        p.c.lvkm = 8;
                        p.c.get().potential0+= 80000;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential2 += 80000;
                        p.c.get().potential3 += 80000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv8 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 8: {
                    if (p.c.lvkm !=8){
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 8 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    if (p.c.expkm < 50000000){
                    p.conn.sendMessageLog("Không đủ 50 triệu EXP kinh mạch để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn <= 70) {
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.upluongMessage(-1000000);
                        p.c.expkm -= 50000000;
                        p.c.lvkm = 9;
                        p.c.get().potential0+= 90000;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential2 += 90000;
                        p.c.get().potential3 += 90000;
                        p.conn.sendMessageLog("con đã nâng thành công kinh mạch hiện tại đang là lv9 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                
                case 11: {
                    Server.manager.sendTB(p, "Điều Kiện học kinh mạch", "Bạn phải tích đủ exp kinh mạch thông qua việc đánh boss lấy sách KINH MACH"
                            + "\n>Kinh mạch<"
                            + "\n-lv1 cần 5 triệu exp Kinh mạch và 100k lượng"
                            + "\n-lv2 cần 10 triệu exp Kinh mạch và 200k lượng"
                            + "\n-lv3 cần 15 triệu exp Kinh mạch và 300k lượng"
                            + "\n-lv4 cần 20 triệu exp Kinh mạch và 400k lượng"
                            + "\n-lv5 cần 25 triệu exp Kinh mạch và 500k lượng"
                            + "\n-lv6 cần 30 triệu exp Kinh mạch và 600k lượng"
                            + "\n-lv7 cần 35 triệu exp Kinh mạch và 700k lượng"
                            + "\n-lv8 cần 40 triệu exp Kinh mạch và 800k lượng"
                            + "\n-lv9 cần 50 triệu exp Kinh mạch và 1 triệu lượng"
                            + "\n-thành công Kinh mạch sẽ lên lv và nhận đc hiệu ứng tương ứng"
                            + "\n-thất bại sẽ mất lượng exp giữ nguyên"
                            + "\n-mỗi tầng sẽ nhận cộng dồn điểm tiềm năng  "
                            + "\n-BẢNG TÍNH ĐIỂM "
                            + "\n-Tầng 1:10k + Tầng 2:20k + Tầng 3:30k + Tầng 4:40k\n"
                            + "Tầng 5:+50k + Tầng 6:60k + Tầng 7:70k + Tầng 8:80k + Tầng 9:90k"
                            );
                    break;
                }
                case 9: {
                    p.KinhMach();
                    break;
                }
                case 10: {
                    p.conn.sendMessageLog("Con đang có : " +p.c.expkm +" Exp kinh mạch");
                    break;
                }
            }
        }
  
    
     public static void npcquocvuong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
       switch(menuId) {  
           case 0: {
                switch (b3) {
                    case 0: {
                    if (p.c.lvttd !=0){
                    p.conn.sendMessageLog("con đã mở Thôn Thiên Địa rồi");
                    return;
                    }
                     else if (p.luong < 100000) {
                    Service.chatNPC(p, (short) npcid, "Cần 100k lượng");
                    break;
                }
                    
                    if (p.c.expttd < 5000000){
                    p.conn.sendMessageLog("Không đủ 5 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách rồi quay lại đây tao chỉ cho");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 100000 ;
                         p.upluongMessage(-100000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 100000 ;
                        p.upluongMessage(-100000);
                        p.c.expttd -= 5000000;
                        p.c.lvttd = 1;
                        p.c.get().potential0+= 5000;
                        p.c.get().potential1 += 5000;
                        p.c.get().potential2 += 5000;
                        p.c.get().potential3 += 5000; 
                       
                        Service.chatNPC(p, (short) npcid,"con đã học thành công Thôn Thiên Địa hiện tại đang là lv1 thoát game để cập nhập tiềm năng");
                        
                    }
                    
                    }
                    
                    break;
            }
                case 1:{
                    if (p.c.lvttd !=1){
                    p.conn.sendMessageLog("Mở Thôn Thiên Địa đi rồi đến gặp tao để nâng");
                    return;
                    }
                   
                         else if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Cần 200000 lượng");
                    break;
                }
                    if (p.c.expttd < 10000000){
                    p.conn.sendMessageLog("Không đủ 10 triệu EXP Thôn Thiên ĐịaThôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 200000 ;
                         p.upluongMessage(-200000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
return;
                    }else{
                        p.c.luongTN =- 200000 ;
                        p.upluongMessage(-200000);
                        p.c.expttd -= 10000000;
                        p.c.lvttd = 2;
                        p.c.get().potential0+= 10000;
                        p.c.get().potential1 += 10000;
                        p.c.get().potential2 += 10000;
                        p.c.get().potential3 += 10000;
                      
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv2 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 2: {
                    if (p.c.lvttd !=2){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 2 đi rồi đến gặp tao để nâng");
                    return;
                    }
                     
                        else if (p.luong < 300000) {
                    Service.chatNPC(p, (short) npcid, "Cần 300000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 15000000){
                    p.conn.sendMessageLog("Không đủ 15 triệu EXP Thôn Thiên Địa để nâng, hãy đi đánh cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        p.c.luongTN =- 300000 ;
                         p.upluongMessage(-300000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 300000 ;
                        p.upluongMessage(-300000);
                        p.c.expttd -= 15000000;
                        p.c.lvttd = 3;
                        p.c.get().potential0+= 15000;
                        p.c.get().potential1 += 15000;
                        p.c.get().potential2 += 15000;
                        p.c.get().potential3 += 15000;
                       
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv3 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 3: {
                    if (p.c.lvttd !=3){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 3 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    else if (p.luong < 400000) {
                    Service.chatNPC(p, (short) npcid, "Cần 400000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 20000000){
                    p.conn.sendMessageLog("Không đủ 20 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        p.c.luongTN =- 400000 ;
                         p.upluongMessage(-400000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 400000 ;
                        p.upluongMessage(-400000);
                        p.c.expttd -= 20000000;
                        p.c.lvttd = 4;
                        p.c.get().potential0+= 20000;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential2 += 20000;
                        p.c.get().potential3 += 20000;
                     
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv4 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 4: {
                    if (p.c.lvttd !=4){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 4 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 25000000){
                    p.conn.sendMessageLog("Không đủ 25 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.expttd -= 25000000;
                        p.c.lvttd = 5;
                        p.c.get().potential0+= 25000;
                        p.c.get().potential1 += 25000;
                        p.c.get().potential2 += 25000;
                        p.c.get().potential3 += 25000;
                      
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv5 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 5: {
                    if (p.c.lvttd !=5){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 5 đi rồi đến gặp tao để nâng");
                    return;
                    }
                   else if (p.luong < 600000) {
                    Service.chatNPC(p, (short) npcid, "Cần 600000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 30000000){
                    p.conn.sendMessageLog("Không đủ 30 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        p.c.luongTN =- 600000 ;
                         p.upluongMessage(-600000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 600000 ;
                        p.upluongMessage(-600000);
                        p.c.expttd -= 30000000;
                        p.c.lvttd = 6;
                        p.c.get().potential0+= 30000;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential2 += 30000;
                        p.c.get().potential3 += 30000;
                       
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv6 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 6: {
                    if (p.c.lvttd !=6){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 6 đi rồi đến gặp tao để nâng");
                    return;
                    }
                   else if (p.luong < 700000) {
                    Service.chatNPC(p, (short) npcid, "Cần 700000 lượng");
                    break;
                }
                      
                    if (p.c.expttd < 35000000){
                    p.conn.sendMessageLog("Không đủ 35 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 700000 ;
                         p.upluongMessage(-700000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 700000 ;
                        p.upluongMessage(-700000);
                        p.c.expttd -= 35000000;
                        p.c.lvttd = 7;
                        p.c.get().potential0+= 35000;
                        p.c.get().potential1 += 35000;
                        p.c.get().potential2 += 35000;
                        p.c.get().potential3 += 35000;
                        
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv7 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 7: {
                    if (p.c.lvttd !=7){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 7 đi rồi đến gặp tao để nâng");
                    return;
                    }
                       else if (p.luong < 800000) {
                    Service.chatNPC(p, (short) npcid, "Cần 800000 lượng");
                    break;
                }
                    
                    if (p.c.expttd < 40000000){
                    p.conn.sendMessageLog("Không đủ 40 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10){
                        p.c.luongTN =- 800000 ;
                         p.upluongMessage(-800000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 800000 ;
                        p.upluongMessage(-800000);
                        p.c.expttd -= 40000000;
                        p.c.lvttd = 8;
                        p.c.get().potential0+= 40000;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential2 += 40000;
                        p.c.get().potential3 += 40000;
                       
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv8 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                  case 8: {
                    if (p.c.lvttd !=8){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 8 đi rồi đến gặp tao để nâng");
                    return;
                    }
                    
                         else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    if (p.c.expttd < 45000000){
                    p.conn.sendMessageLog("Không đủ 45 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 45000000;
                        p.c.lvttd = 9;
                        p.c.get().potential0+= 45000;
                        p.c.get().potential1 += 45000;
                        p.c.get().potential2 += 45000;
                        p.c.get().potential3 += 45000;
                       
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv9 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 9: {
                    if (p.c.lvttd !=9){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 9 đi rồi đến gặp tao để nâng");
                    return;
                    }
                        else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    if (p.c.expttd < 50000000){
                    p.conn.sendMessageLog("Không đủ 50 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 50000000;
                        p.c.lvttd = 10;
                        p.c.get().potential0+= 50000;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential2 += 50000;
                        p.c.get().potential3 += 50000;
                         
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv10 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                  case 10: {
                    if (p.c.lvttd !=10){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 10 đi rồi đến gặp tao để nâng");
                    return;
                    }  else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    if (p.c.expttd < 55000000){
                    p.conn.sendMessageLog("Không đủ 55 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 55000000;
                        p.c.lvttd = 11;
                        p.c.get().potential0+= 55000;
                        p.c.get().potential1 += 55000;
                        p.c.get().potential2 += 55000;
                        p.c.get().potential3 += 55000;
                      
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv11 thoát game để cập nhập tiềm năng");
                    }
                    }
break;
            }
                    case 11: {
                    if (p.c.lvttd !=11){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 11 đi rồi đến gặp tao để nâng");
                    return;
                    }
                      else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    if (p.c.expttd < 60000000){
                    p.conn.sendMessageLog("Không đủ 60 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 60000000;
                        p.c.lvttd = 12;
                        p.c.get().potential0+= 60000;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential2 += 60000;
                        p.c.get().potential3 += 60000;
                        
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv12 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                      case 12: {
                    if (p.c.lvttd !=12){
p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 12 đi rồi đến gặp tao để nâng");
                    return;
                    }
                         else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 65000000){
                    p.conn.sendMessageLog("Không đủ 65 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 65000000;
                        p.c.lvttd = 13;
                        p.c.get().potential0+= 65000;
                        p.c.get().potential1 += 65000;
                        p.c.get().potential2 += 65000;
                        p.c.get().potential3 += 65000;
                       
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv13 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                        case 13: {
                    if (p.c.lvttd !=13){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 13 đi rồi đến gặp tao để nâng");
                    return;
                    }
                        else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    
if (p.c.expttd < 70000000){
                    p.conn.sendMessageLog("Không đủ 70 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 70000000;
                        p.c.lvttd = 14;
                        p.c.get().potential0+= 70000;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential2 += 70000;
                        p.c.get().potential3 += 70000;
                       
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv14 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                case 14: {
                    if (p.c.lvttd !=14){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 14 đi rồi đến gặp tao để nâng");
                    return;
                    }
                     else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    if (p.c.expttd < 75000000){
                    p.conn.sendMessageLog("Không đủ 75 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 75000000;
                        p.c.lvttd = 15;
                        p.c.get().potential0+= 75000;
                        p.c.get().potential1 += 75000;
                        p.c.get().potential2 += 75000;
                        p.c.get().potential3 += 75000;
                      
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv15 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                  case 15: {
                    if (p.c.lvttd !=15){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 15 đi rồi đến gặp tao để nâng");
                    return;
                    }
                        else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 80000000){
                    p.conn.sendMessageLog("Không đủ 80 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn >10) {
                        p.c.luongTN =- 1000000 ;
p.upluongMessage(-1000000);
                         p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 80000000;
                        p.c.lvttd = 16;
                        p.c.get().potential0+= 80000;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential2 += 80000;
                        p.c.get().potential3 += 80000;
                      
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv16 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                    case 16: {
                    if (p.c.lvttd !=16){
                    p.conn.sendMessageLog("Nâng Thôn Thiên Địa lên cấp 16 đi rồi đến gặp tao để nâng");
                    return;
                    }
                      else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000000 lượng");
                    break;
                }
                    
                    
                    if (p.c.expttd < 85000000){
                    p.conn.sendMessageLog("Không đủ 85 triệu EXP Thôn Thiên Địa để nâng, hãy đi cắn sách đi rồi đến đây gặp ta");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
p.conn.sendMessageLog("tư chất con còn kém lắm về luyện thêm đi rồi đến đây nhé, ta xin tiền học phí");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expttd -= 85000000;
                        p.c.lvttd = 17;
                        p.c.get().potential0+= 85000;
                        p.c.get().potential1 += 85000;
                        p.c.get().potential2 += 85000;
                        p.c.get().potential3 += 85000;
                     
                        p.conn.sendMessageLog("con đã nâng thành công Thôn Thiên Địa hiện tại đang là lv17 thoát game để cập nhập tiềm năng");
                    }
                    }
                    break;
            }
                  case 17: {
                    p.ThonThienDia();
                    break;
                }
                case 18: {
                    p.conn.sendMessageLog("Con đang có : " +p.c.expttd +" Exp Thôn Thiên Địa");
                    break;
                }
              
                case 19: {
                    Server.manager.sendTB(p, "Điều Kiện học Thôn Thiên Địa", "Bạn phải tích đủ exp Thôn Thiên Địa thông qua việc đánh boss lấy sách Thôn Thiên Địa"
                            + "\n>Thôn Thiên Địa<"
                            + "\n-lv1 cần 5 triệu exp Thôn Thiên Địa và 100k lượng"
                            + "\n-lv2 cần 10 triệu exp Thôn Thiên Địa và 200k lượng"
                            + "\n-lv3 cần 15 triệu exp Thôn Thiên Địavà 300k lượng"
                            + "\n-lv4 cần 20 triệu exp Thôn Thiên Địa và 400k lượng"
                            + "\n-lv5 cần 25 triệu exp Thôn Thiên Địa và 500k lượng"
                            + "\n-lv6 cần 30 triệu exp Thôn Thiên Địa và 600k lượng"
                            + "\n-lv7 cần 35 triệu exp Thôn Thiên Địa và 700k lượng"
                            + "\n-lv8 cần 40 triệu exp Thôn Thiên Địa và 800k lượng"
                            + "\n-lv9 cần 45 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv10 cần 50 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv11 cần 55 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv12 cần 60 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv13 cần 65 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv14 cần 70 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv15 cần 75 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv16 cần 80 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-lv17 cần 85 triệu exp Thôn Thiên Địa và 1 triệu lượng"
                            + "\n-thành công Thôn Thiên Địa sẽ lên lv và nhận đc hiệu ứng tương ứng"
                            + "\n-thất bại sẽ mất lượng exp giữ nguyên"
                            + "\n-mỗi tầng sẽ nhận cộng dồn điểm tiềm năng  "
                            + "\n-BẢNG TÍNH ĐIỂM "
                            + "\n-Tầng 1:5k + Tầng 2:10k + Tầng 3:15k + Tầng 4:20k "
                            + "\nTầng 5:+25k + Tầng 6:30k + Tầng 7:35k + Tầng 8:40k + Tầng 9:45k"
                            + "\nTầng 10:+50k + Tầng 11:55k + Tầng 12:60k + Tầng 13:65k + Tầng 14:70k"
                            + "\nTầng 15:+75k + Tầng 16:80k + Tầng 17:85k  "
                            );
                    break;
                } 
            }
                break; 
        }
           
           case 1:{  
              switch (b3) {
                case 0: {
                    if (p.luong < 100000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 100k lượng thì biến!");
                    return;
                    }if (p.c.lvkm != 0) {
                    p.conn.sendMessageLog("võ giả đã bắt đầu bước trên con đường tu luyện");
                    return;
                    }
                    if (p.c.expkm < 5000000){
                    p.conn.sendMessageLog("Không đủ 5 triệu EXP đấu khí, hãy đi đánh boss rồi quay lại đây tu luyện");
                    return;
                    }else {
                    
                       
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                       
                         p.c.luongTN =- 100000 ;
                         p.upluongMessage(-100000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         
                    Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " ); 
                    return;
                    }else{
                        
                        p.c.luongTN =- 100000 ;
                        p.upluongMessage(-100000);
                        p.c.expkm -= 5000000;
                        p.c.lvkm = 1;
                        p.c.get().potential0+= 10000;
                        p.c.get().potential1 += 10000;
                        p.c.get().potential2 += 10000;
                        p.c.get().potential3 += 10000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu khí vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu khí  " );
                    }
                    }
                    break;
            }
                case 1:{
                    if (p.luong < 100000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 100k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=1){
                    p.conn.sendMessageLog("võ giả cần đạt cấp bậc đấu khí");
                    return;
                    }
                    if (p.c.expkm < 10000000){
                    p.conn.sendMessageLog("Không đủ 10 triệu EXP đấu khí, hãy tu luyện thêm");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        
                        p.c.luongTN =- 100000 ;
                         p.upluongMessage(-100000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );  
                         return;
                    }else{
                        p.c.luongTN =- 100000 ;
                        p.upluongMessage(-100000);
                        p.c.expkm -= 10000000;
                        p.c.lvkm = 2;
                        p.c.get().potential0+= 20000;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential2 += 20000;
                        p.c.get().potential3 += 20000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu giả vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu giả  " );
                    }
                    }
                    break;
            }
                case 2: {
                    if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 200k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=2){
                    p.conn.sendMessageLog("võ giả cần đại đến cấp bậc đấu giả");
                    return;
                    }
                    if (p.c.expkm < 15000000){
                    p.conn.sendMessageLog("Không đủ 15 triệu EXP đấu khí, hãy tu luyện thêm");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                      
                        p.c.luongTN =- 200000 ;
                         p.upluongMessage(-200000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.c.luongTN =- 200000 ;
                        p.upluongMessage(-200000);
                        p.c.expkm -= 15000000;
                        p.c.lvkm = 3;
                        p.c.get().potential0+= 30000;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential2 += 30000;
                        p.c.get().potential3 += 30000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu sư vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu sư  " );
                    }
                    }
                    break;
            }
                case 3: {
                    if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 200k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=3){
                    p.conn.sendMessageLog("võ giả cần đại đến cấp bậc đấu sư");
                    return;
                    }
                    if (p.c.expkm < 20000000){
                    p.conn.sendMessageLog("Không đủ 20 triệu EXP đấu khí, hãy tu luyện thêm");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        
                    
                         p.upluongMessage(-200000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.c.luongTN =- 200000 ;
                        p.upluongMessage(-200000);
                        p.c.expkm -= 20000000;
                        p.c.lvkm = 4;
                        p.c.get().potential0+= 40000;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential2 += 40000;
                        p.c.get().potential3 += 40000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu linh vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu linh  " );
                    }
                    }
                    break;
            }
                case 4: {
                    if (p.luong < 300000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 200k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=4){
                    p.conn.sendMessageLog("võ giả cần đạt tới cấp độ đấu linh");
                    return;
                    }
                    if (p.c.expkm < 25000000){
                    p.conn.sendMessageLog("Không đủ 25 triệu EXP đấu khí, hãy tu luyện thêm");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        
                        p.c.luongTN =- 300000 ;
                         p.upluongMessage(-300000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.c.luongTN =- 300000 ;
                        p.upluongMessage(-300000);
                        p.c.expkm -= 25000000;
                        p.c.lvkm = 5;
                        p.c.get().potential0+= 50000;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential2 += 50000;
                        p.c.get().potential3 += 50000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu vương vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu vương  " );
                    }
                    }
                    break;
            }
                case 5: {
                    if (p.luong < 300000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 300k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=5){
                    p.conn.sendMessageLog("võ giả cần đạt đến cấp bậc đấu vương");
                    return;
                    }
                    if (p.c.expkm < 30000000){
                    p.conn.sendMessageLog("Không đủ 30 triệu EXP đáu khí, hãy tu luyện thêm");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                       
                        p.c.luongTN =- 300000 ;
                         p.upluongMessage(-300000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.c.luongTN =- 300000 ;
                        p.upluongMessage(-300000);
                        p.c.expkm -= 30000000;
                        p.c.lvkm = 6;
                        p.c.get().potential0+= 60000;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential2 += 60000;
                        p.c.get().potential3 += 60000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu hoàng vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu hoàng  " );
                    }
                    }
                    break;
            }
                case 6: {
                    if (p.luong < 400000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 400k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=6){
                    p.conn.sendMessageLog("võ giả cần đạt tới cấp bậc đấu hoàng");
                    return;
                    }
                    if (p.c.expkm < 35000000){
                    p.conn.sendMessageLog("Không đủ 35 triệu EXP đấu khí, hãy tu luyện thêm đi");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        
                        p.c.luongTN =- 400000 ;
                         p.upluongMessage(-400000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.c.luongTN =- 400000 ;
                        p.upluongMessage(-400000);
                        p.c.expkm -= 35000000;
                        p.c.lvkm = 7;
                        p.c.get().potential0+= 70000;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential2 += 70000;
                        p.c.get().potential3 += 70000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu tông vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu tông  " );
                    }
                    }
                    break;
            }
                case 7: {
                    if (p.luong < 400000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 400k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=7){
                    p.conn.sendMessageLog("võ giả cần đạt tới cấp độ đấu tông");
                    return;
                    }
                    Service.chatNPC(p, (short) npcid, "Không đủ 50k lượng thì biến!");
                    if (p.c.expkm < 40000000){
                    p.conn.sendMessageLog("Không đủ 40 triệu EXP đấu khí, hãy tu luyện thêm đi");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 10) {
                        
                        
                        p.c.luongTN =- 400000 ;
                         p.upluongMessage(-400000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.upluongMessage(-400000);
                        p.c.expkm -= 40000000;
                        p.c.lvkm = 8;
                        p.c.get().potential0+= 80000;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential2 += 80000;
                        p.c.get().potential3 += 80000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu tôn vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu tôn  " );
                    }
                    }
                    break;
            }
                case 8: {
                    if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Không đủ 500k lượng thì biến!");
                    return;
                    }if (p.c.lvkm !=8){
                    p.conn.sendMessageLog("võ giả cần đạt tới cấp bậc đấu tôn");
                    return;
                    }
                    if (p.c.expkm < 50000000){
                    p.conn.sendMessageLog("Không đủ 50 triệu EXP đấu khí, hãy tu luyện thêm đi");
                    return;
                    } else{
                    byte pkoolvn = (byte) Util.nextInt(1, 100);
                    if (pkoolvn > 5) {
                        
                        p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.conn.sendMessageLog("đột phá thất bại đừng nản chí hay tu luyện lại");
                         Server.manager.chatKTG("Người chơi " + p.c.name + "đột phá thất bại nhân phẩm của bạn cần xem xét lại " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.expkm -= 50000000;
                        p.c.lvkm = 9;
                        p.c.get().potential0+= 90000;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential2 += 90000;
                        p.c.get().potential3 += 90000;
                        p.conn.sendMessageLog("chúc mừng võ giả tu luyện thành công cấp bậc đấu đế vui lòng thoát game để cập nhập tiềm năng");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " tu luyện thành công cấp bậc đấu đế  " );
                    }
                    }
                    break;
            }
                
                case 11: {
                    Server.manager.sendTB(p, "Điều Kiện tu luyện đấu khí", "võ giả phải tích đủ exp đấu khí bằng cách đánh boss lấy sách đấu khí"
                            + "\n>Đấu khí<"
                            + "\n-lv1 cần 5 triệu exp đấu khí và 100k lượng với tỉ lệ thành công là 90%"
                            + "\n-lv2 cần 10 triệu exp đấu khí và 100k lượng với tỉ lệ thành công là 80%"
                            + "\n-lv3 cần 15 triệu exp đấu khí và 200k lượng với tỉ lệ thành công là 70%"
                            + "\n-lv4 cần 20 triệu exp đấu khí và 200k lượng với tỉ lệ thành công là 60%"
                            + "\n-lv5 cần 25 triệu exp đấu khí và 300k lượng với tỉ lệ thành công là 50%"
                            + "\n-lv6 cần 30 triệu exp đấu khí và 300k lượng với tỉ lệ thành công là 35%"
                            + "\n-lv7 cần 35 triệu exp đấu khí và 400k lượng với tỉ lệ thành công là 20%"
                            + "\n-lv8 cần 40 triệu exp đấu khí và 400k lượng với tỉ lệ thành công là 10%"
                            + "\n-lv9 cần 50 triệu exp đấu khí và 500k lượng với tỉ lệ thành công là 5%"
                            + "\n-đột phá thành công sẽ cấp bậc và nhận đc hiệu ứng tương ứng"
                            + "\n-thất bại sẽ mất lượng exp giữ nguyên"
                            + "\n-mỗi cấp bậc sẽ nhận cộng dồn điểm tiềm năng  "
                            + "\n-BẢNG cấp bậc "
                            + "\n-đấu khí :10k + đấu giả :20k + đấu sư:30k + đấu linh :40k\n"
                            + "đấu vương:+50k + đấu hoàng:60k + đấu tông:70k + đấu tôn:80k + đấu đế:90k"
                            );
                    break;
                }
                case 9: {
                    p.KinhMach();
                    break;
                }
                case 10: {
                    p.conn.sendMessageLog("võ giả : " +p.c.expkm +" Exp đấu khí");
                    break;
                }
            }
               break;
        }
  
           
            }
        }
            
    
    
    
    public static void npcVip(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException{
        short [] nam = {712,713,746,747,748,749,750,751,752};
        short [] nu = {715,716,753,754,755,756,757,758,759};       
        switch(menuId){
            case 0:{
                switch (b3){
                    case 0:{
                        if(p.coinnap >= 50000 && p.c.vip < 1){                                                                              
                            if (p.c.getBagNull() < 10) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if(p.c.gender == 1){
                                p.c.addItemBag(false, ItemTemplate.itemDefault(712));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(713));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(746));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(747));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(748));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(749));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(750));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(751));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(752));
                            }else{
                                p.c.addItemBag(false, ItemTemplate.itemDefault(715));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(716));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(753));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(754));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(755));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(756));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(757));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(758));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(759));
                            }
                            p.upluongMessage(100000);
                            p.c.upxuMessage(100000);
                            p.c.vip = 1;
                            Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 1:{
                         if(p.coinnap >= 100000 && p.c.vip == 1 && p.c.vip < 2){                                                                                
                            if (p.c.getBagNull() < 10) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if(p.c.gender == 1){
                                for(byte i = 0; i < 9; i++){                                    
                                    Item itemup = ItemTemplate.itemDefault(nam[i]);
                                    itemup.upgradeNext((byte)8);                                   
                                    p.c.addItemBag(false, itemup);
                                }
                            }else{
                                for(byte i = 0; i < 9; i++){                                    
                                    Item itemup = ItemTemplate.itemDefault(nu[i]);
                                    itemup.upgradeNext((byte)8);                                   
                                    p.c.addItemBag(false, itemup);
                                }                            
                            }
                            p.c.vip = 2;
                            Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 2:{
                        if(p.coinnap >= 150000 && p.c.vip == 2 && p.c.vip < 3){                                                                            
                            if (p.c.getBagNull() < 10) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if(p.c.gender == 1){
                                for(byte i = 0; i < 9; i++){                                    
                                    Item itemup = ItemTemplate.itemDefault(nam[i]);
                                    itemup.upgradeNext((byte)16);                                   
                                    p.c.addItemBag(false, itemup);
                                }
                            }else{
                                for(byte i = 0; i < 9; i++){                                    
                                    Item itemup = ItemTemplate.itemDefault(nu[i]);
                                    itemup.upgradeNext((byte)16);                                   
                                    p.c.addItemBag(false, itemup);
                                }                            
                            }
                            p.c.vip = 3;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 3:{
                        if(p.coinnap >= 200000 && p.c.vip == 3 && p.c.vip < 4){                                                                              
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(875));                          
                            p.c.vip = 4;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 4:{
                        short [] ngokhong = {835, 836};
                        if(p.coinnap >= 250000 && p.c.vip == 4 && p.c.vip < 5){                                                                               
                            if (p.c.getBagNull() < 4) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.get().nclass == 0) {
                                p.conn.sendMessageLog("Trùm cần nhập học để nhận vip 5");
                                return;
                            }
                                for(byte i = 0; i < 2; i++){
                                    Item itemup = ItemTemplate.itemDefault(ngokhong[i]);  
                                    itemup.upgradeNext((byte)16);
                                    p.c.addItemBag(false, itemup);                                                                  
                                }
                                p.c.addItemBag(false, ItemTemplate.itemDefault(837));
                                if (p.c.get().nclass == 1 || p.c.get().nclass == 3 || p.c.get().nclass == 5) {
                                    Item itemup = ItemTemplate.itemDefault(833);
                                    if (p.c.get().nclass == 1) {
                                        itemup.sys = 1;
                                    } else if (p.c.get().nclass == 3) {
                                        itemup.sys = 2;
                                    } else if (p.c.get().nclass == 5) {
                                        itemup.sys = 3;
                                    }
                                    itemup.upgradeNext((byte)16);
                                    p.c.addItemBag(false, itemup);
                                }
                                if (p.c.get().nclass == 2 || p.c.get().nclass == 4 || p.c.get().nclass == 6) {
                                    Item itemup = ItemTemplate.itemDefault(834);
                                    if (p.c.get().nclass == 2) {
                                        itemup.sys = 1;
                                    } else if (p.c.get().nclass == 4) {
                                        itemup.sys = 2;
                                    } else if (p.c.get().nclass == 6) {
                                        itemup.sys = 3;
                                    }
                                    itemup.upgradeNext((byte)16);
                                    p.c.addItemBag(false, itemup);
                                }
                            p.c.vip = 5;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 5:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(p.coinnap >= 300000 && p.c.vip == 5 && p.c.vip < 6){
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            Item itemup = ItemTemplate.itemDefault(853);
                            p.c.addItemBag(false, itemup);
                            p.c.vip = 6;
                          Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 6:{
                        if(p.coinnap >= 350000 && p.c.vip == 6 && p.c.vip < 7){                                                                              
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(872));                          
                            p.c.vip = 7;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                
            case 7:{
                       short [] set11x = {972, 973};
                        if(p.coinnap >= 400000 && p.c.vip == 7 && p.c.vip < 8){                                                                               
                            if (p.c.getBagNull() < 4) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.get().nclass == 0) {
                                p.conn.sendMessageLog("Trùm cần nhập học để nhận vip 8");
                                return;
                            }
                                for(byte i = 0; i < 2; i++){
                                    Item itemup = ItemTemplate.itemDefault(set11x[i]);  
                                    itemup.upgradeNext((byte)17);
                                    p.c.addItemBag(false, itemup);                                                                  
                                }
                                p.c.addItemBag(false, ItemTemplate.itemDefault(974));
                                if (p.c.get().nclass == 1 || p.c.get().nclass == 3 || p.c.get().nclass == 5) {
                                    Item itemup = ItemTemplate.itemDefault(974);
                                    if (p.c.get().nclass == 1) {
                                        itemup.sys = 1;
                                    } else if (p.c.get().nclass == 3) {
                                        itemup.sys = 2;
                                    } else if (p.c.get().nclass == 5) {
                                        itemup.sys = 3;
                                    }
                                    itemup.upgradeNext((byte)17);
                                    p.c.addItemBag(false, itemup);
                                }
                                if (p.c.get().nclass == 2 || p.c.get().nclass == 4 || p.c.get().nclass == 6) {
                                    Item itemup = ItemTemplate.itemDefault(975);
                                    if (p.c.get().nclass == 2) {
                                        itemup.sys = 1;
                                    } else if (p.c.get().nclass == 4) {
                                        itemup.sys = 2;
                                    } else if (p.c.get().nclass == 6) {
                                        itemup.sys = 3;
                                    }
                                    itemup.upgradeNext((byte)17);
                                    p.c.addItemBag(false, itemup);
                                }
                            p.c.vip = 8;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
            case 8:{
                        if(p.coinnap >= 600000 && p.c.vip == 12 && p.c.vip < 13){                                                                               
                            if (p.c.getBagNull() < 4) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.get().nclass == 0) {
                                p.conn.sendMessageLog("Trùm cần nhập học để nhận vip 12");
                                return;
                            }
                            short[] arId = new short[]{1009,1010,1011,1012,1013,1014};
                      short idI = arId[Util.nextInt(arId.length)];
                  Item itemup = ItemTemplate.itemDefault(idI);
                     itemup.isLock = false;
                    itemup = ItemTemplate.itemDefault(idI);
                    short cs2 = (short) Util.nextInt(1,16);
                     itemup.upgrade = 16;
                     itemup.options.clear();
                     short cs = (short) Util.nextInt(20000,25000);
                     short cs1 = (short) Util.nextInt(100,250);
                      short cs3 = (short) Util.nextInt(20,50);
                      short cs4 = (short) Util.nextInt(550,560);

                        itemup.options.add(new Option(73, cs*cs2));
                        itemup.options.add(new Option(6, cs*cs2));
                        itemup.options.add(new Option(7,cs*cs2));
                        itemup.options.add(new Option(0, cs*cs2));
                        itemup.options.add(new Option(1, cs*cs2));
                        itemup.options.add(new Option(8, cs4));
                        itemup.options.add(new Option(9, cs4));
                        itemup.options.add(new Option(10, cs*cs2));
                        itemup.options.add(new Option(14, cs4));
                        itemup.options.add(new Option(18, cs*cs2));
                        itemup.options.add(new Option(27, cs));
                        itemup.options.add(new Option(30, cs));
                        itemup.options.add(new Option(33, cs*cs2));
                        itemup.options.add(new Option(34, cs*cs2));
                        itemup.options.add(new Option(35, cs*cs2));
                        itemup.options.add(new Option(37, cs4));
                        itemup.options.add(new Option(38, cs*cs2));
                        itemup.options.add(new Option(54, cs4));
                        itemup.options.add(new Option(56, cs4));
                        itemup.options.add(new Option(57, cs));
                        itemup.options.add(new Option(58, cs4));
                        itemup.options.add(new Option(60, cs4));
                        itemup.options.add(new Option(61, cs4));
                        itemup.options.add(new Option(67, cs4));                        
                        itemup.options.add(new Option(100, cs4));
                        itemup.options.add(new Option(101, cs4));
                        itemup.options.add(new Option(102, cs1));
                        itemup.options.add(new Option(103, cs1));
                        itemup.options.add(new Option(85, 10)); 
                        itemup.options.add(new Option(86, cs));        
                        itemup.options.add(new Option(87, cs)); 
                        itemup.options.add(new Option(88, cs)); 
                        itemup.options.add(new Option(89, cs)); 
                        itemup.options.add(new Option(90, cs)); 
                        itemup.options.add(new Option(91, cs)); 
                        itemup.options.add(new Option(92, cs)); 
                        itemup.options.add(new Option(94, cs4)); 
                        itemup.options.add(new Option(95, cs)); 
                        itemup.options.add(new Option(96, cs)); 
                        itemup.options.add(new Option(97, cs)); 
                        itemup.options.add(new Option(98, cs3)); 
                        itemup.options.add(new Option(99, cs));
                        itemup.options.add(new Option(124,cs*cs4));
                        itemup.options.add(new Option(126, cs4)); 
                        itemup.options.add(new Option(127, cs4)); 
                        itemup.options.add(new Option(128, cs4)); 
                        itemup.options.add(new Option(130, cs4));
                        p.c.addItemBag(false, itemup);
                        p.c.addItemBag(true, itemup);
                        p.luong +=12000000;
                            p.c.vip = 13;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
            case 9:{
                        if(p.coinnap >= 750000 && p.c.vip == 15 && p.c.vip < 16){                                                                               
                            if (p.c.getBagNull() < 4) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.get().nclass == 0) {
                                p.conn.sendMessageLog("Trùm cần nhập học để nhận vip 15");
                                return;
                            }
                            short[] arId = new short[]{1009,1010,1011,1012,1013,1014};
                      short idI = arId[Util.nextInt(arId.length)];
                  Item itemup = ItemTemplate.itemDefault(idI);
                     itemup.isLock = false;
                    itemup = ItemTemplate.itemDefault(idI);
                    short cs2 = (short) Util.nextInt(1,16);
                     itemup.upgrade = 16;
                     itemup.options.clear();
                     short cs = (short) Util.nextInt(20000,25000);
                     short cs1 = (short) Util.nextInt(100,250);
                      short cs3 = (short) Util.nextInt(20,50);
                      short cs4 = (short) Util.nextInt(650,700);

                        itemup.options.add(new Option(73, cs*cs2));
                        itemup.options.add(new Option(6, cs*cs2));
                        itemup.options.add(new Option(7,cs*cs2));
                        itemup.options.add(new Option(0, cs*cs2));
                        itemup.options.add(new Option(1, cs*cs2));
                        itemup.options.add(new Option(8, cs4));
                        itemup.options.add(new Option(9, cs4));
                        itemup.options.add(new Option(10, cs*cs2));
                        itemup.options.add(new Option(14, cs4));
                        itemup.options.add(new Option(18, cs*cs2));
                        itemup.options.add(new Option(27, cs));
                        itemup.options.add(new Option(30, cs));
                        itemup.options.add(new Option(33, cs*cs2));
                        itemup.options.add(new Option(34, cs*cs2));
                        itemup.options.add(new Option(35, cs*cs2));
                        itemup.options.add(new Option(37, cs4));
                        itemup.options.add(new Option(38, cs*cs2));
                        itemup.options.add(new Option(54, cs4));
                        itemup.options.add(new Option(56, cs4));
                        itemup.options.add(new Option(57, cs));
                        itemup.options.add(new Option(58, cs4));
                        itemup.options.add(new Option(60, cs4));
                        itemup.options.add(new Option(61, cs4));
                        itemup.options.add(new Option(67, cs4));                        
                        itemup.options.add(new Option(100, cs4));
                        itemup.options.add(new Option(101, cs4));
                        itemup.options.add(new Option(102, cs1));
                        itemup.options.add(new Option(103, cs1));
                        itemup.options.add(new Option(85, 10)); 
                        itemup.options.add(new Option(86, cs));        
                        itemup.options.add(new Option(87, cs)); 
                        itemup.options.add(new Option(88, cs)); 
                        itemup.options.add(new Option(89, cs)); 
                        itemup.options.add(new Option(90, cs)); 
                        itemup.options.add(new Option(91, cs)); 
                        itemup.options.add(new Option(92, cs)); 
                        itemup.options.add(new Option(94, cs4)); 
                        itemup.options.add(new Option(95, cs)); 
                        itemup.options.add(new Option(96, cs)); 
                        itemup.options.add(new Option(97, cs)); 
                        itemup.options.add(new Option(98, cs3)); 
                        itemup.options.add(new Option(99, cs));
                        itemup.options.add(new Option(124,cs*cs4));
                        itemup.options.add(new Option(126, cs4)); 
                        itemup.options.add(new Option(127, cs4)); 
                        itemup.options.add(new Option(128, cs4)); 
                        itemup.options.add(new Option(130, cs4));
                        p.c.addItemBag(false, itemup);
                        p.c.addItemBag(true, itemup);
                        p.c.addItemBag(true, itemup);
                            p.c.vip = 16;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
            case 10:{
                        if(p.coinnap >= 900000 && p.c.vip == 18 && p.c.vip < 19){                                                                               
                            if (p.c.getBagNull() < 4) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.get().nclass == 0) {
                                p.conn.sendMessageLog("Trùm cần nhập học để nhận vip 18");
                                return;
                            }
                            short[] arId = new short[]{1023,1024,1025,1026};
                      short idI = arId[Util.nextInt(arId.length)];
                  Item itemup = ItemTemplate.itemDefault(idI);
                     itemup.isLock = false;
                    itemup = ItemTemplate.itemDefault(idI);
                    short cs2 = (short) Util.nextInt(1,16);
                     itemup.upgrade = 16;
                     itemup.options.clear();
                     short cs = (short) Util.nextInt(20000,25000);
                     short cs1 = (short) Util.nextInt(100,250);
                      short cs3 = (short) Util.nextInt(20,50);
                      short cs4 = (short) Util.nextInt(750,800);

                        itemup.options.add(new Option(73, cs*cs2));
                        itemup.options.add(new Option(6, cs*cs2));
                        itemup.options.add(new Option(7,cs*cs2));
                        itemup.options.add(new Option(0, cs*cs2));
                        itemup.options.add(new Option(1, cs*cs2));
                        itemup.options.add(new Option(8, cs4));
                        itemup.options.add(new Option(9, cs4));
                        itemup.options.add(new Option(10, cs*cs2));
                        itemup.options.add(new Option(14, cs4));
                        itemup.options.add(new Option(18, cs*cs2));
                        itemup.options.add(new Option(27, cs));
                        itemup.options.add(new Option(30, cs));
                        itemup.options.add(new Option(33, cs*cs2));
                        itemup.options.add(new Option(34, cs*cs2));
                        itemup.options.add(new Option(35, cs*cs2));
                        itemup.options.add(new Option(37, cs4));
                        itemup.options.add(new Option(38, cs*cs2));
                        itemup.options.add(new Option(54, cs4));
                        itemup.options.add(new Option(56, cs4));
                        itemup.options.add(new Option(57, cs));
                        itemup.options.add(new Option(58, cs4));
                        itemup.options.add(new Option(60, cs4));
                        itemup.options.add(new Option(61, cs4));
                        itemup.options.add(new Option(67, cs4));                        
                        itemup.options.add(new Option(100, cs4));
                        itemup.options.add(new Option(101, cs4));
                        itemup.options.add(new Option(102, cs1));
                        itemup.options.add(new Option(103, cs1));
                        itemup.options.add(new Option(85, 10)); 
                        itemup.options.add(new Option(86, cs));        
                        itemup.options.add(new Option(87, cs)); 
                        itemup.options.add(new Option(88, cs)); 
                        itemup.options.add(new Option(89, cs)); 
                        itemup.options.add(new Option(90, cs)); 
                        itemup.options.add(new Option(91, cs)); 
                        itemup.options.add(new Option(92, cs)); 
                        itemup.options.add(new Option(94, cs4)); 
                        itemup.options.add(new Option(95, cs)); 
                        itemup.options.add(new Option(96, cs)); 
                        itemup.options.add(new Option(97, cs)); 
                        itemup.options.add(new Option(98, cs3)); 
                        itemup.options.add(new Option(99, cs));
                        itemup.options.add(new Option(124,cs*cs4));
                        itemup.options.add(new Option(126, cs4)); 
                        itemup.options.add(new Option(127, cs4)); 
                        itemup.options.add(new Option(128, cs4)); 
                        itemup.options.add(new Option(130, cs4));
                        p.c.addItemBag(false, itemup);
                        p.c.addItemBag(true, itemup);
                        p.c.addItemBag(true, itemup);
                            p.c.vip = 19;
                           Service.chatNPC(p, (short) npcid, "nâng vip thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            
                break;
                        }
                        Service.chatNPC(p, (short) npcid, "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                }
                break;
            }
            
            case 1:{
                switch(p.c.vip){
                    case 1:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 10;                                   
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(50000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Báo danh VIP 1 thành công, con nhận được 50000 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 2:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 2) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 20;                                   
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(100000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Trùm báo danh VIP 2 thành công, trùm nhận được 100000 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 3:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 3) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 30;                                   
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(500000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Trùm báo danh VIP 3 thành công, trùm nhận được 500000 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 4:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 2) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 40;                          
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(1000000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Trùm báo danh VIP 4 thành công, trùm nhận được 1000000 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 5:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 6) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 50;                          
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(1500000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Trùm báo danh VIP 5 thành công, trùm nhận được 1tr5 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 6:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 6) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(1));
                            Item itemup = ItemTemplate.itemDefault(743);
                            itemup.quantity = 100;                          
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(2000000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Trùm báo danh VIP 6 thành công, trùm nhận được 2tr lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 7:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 37) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            Item itemup = ItemTemplate.itemDefault(743);
                            itemup.quantity = 200;                                   
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(5000000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Báo danh VIP 12 thành công, con nhận được 50000 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                    case 8:{
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 7) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                             p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(967));
                            Item itemup = ItemTemplate.itemDefault(743);
                            itemup.quantity = 400;                                   
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(5000000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, (short) npcid, "Báo danh VIP 18 thành công, con nhận được 50000 lượng.");
                            break;
                        } else {
                            Service.chatNPC(p, (short) npcid, "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        }
                        break;
                    }
                }
                break;
            }
            case 2:{
                switch (p.c.vip){
                    case 1:{
                        if(p.c.yen >= 500000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-500000);
                            p.c.upxuMessage(500000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    case 2:{
                        if(p.c.yen >= 1000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-1000000);
                            p.c.upxuMessage(1000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    case 3:{
                        if(p.c.yen >= 2000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-2000000);
                            p.c.upxuMessage(2000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    
                    case 4:{
                        if(p.c.yen >= 2000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-5000000);
                            p.c.upxuMessage(5000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    
                    case 5:{
                        if(p.c.yen >= 15000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-15000000);
                            p.c.upxuMessage(15000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    
                    case 6:{
                        if(p.c.yen >= 30000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-30000000);
                            p.c.upxuMessage(30000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    case 7:{
                        if(p.c.yen >= 50000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-50000000);
                            p.c.upxuMessage(50000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }
                    case 8:{
                        if(p.c.yen >= 100000000 && p.c.isQuaHangDong == 0){
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-100000000);
                            p.c.upxuMessage(100000000);
                            Service.chatNPC(p, (short) npcid, "Đổi xu thành công!");
                            break;
                        }else if(p.c.isQuaHangDong != 0){
                            Service.chatNPC(p, (short) npcid, "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }else{
                            Service.chatNPC(p, (short) npcid, "Không đủ yên!");
                        }
                        break;
                    }

                    
                }
                break;
            }
            case 3:{
                switch(b3){
                    case 0:{
                        if(p.c.vip < 1){
                            Service.chatNPC(p, (short) npcid, "Vip 1 mới được sử dụng chức năng bật tắt exp");
                            return;
                        }else{
                            if (p.c.isNhanban) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                                return;
                            }
                            if(p.c.level == 1) {
                                p.conn.sendMessageLog("Không thể thực hiện thao tác này..");
                                return;
                            }
                            if(p.c.get().exptype == 1) {
                                p.c.get().exptype = 0;
                                p.sendAddchatYellow("Đã tắt nhận exp.");
                            } else {
                                p.c.get().exptype = 1;
                                p.sendAddchatYellow("Đã bật nhận exp.");
                            }
                        }
                        break;
                    }
                    case 1:{
                        if(p.c.maxluggage >= 120){
                                Service.chatNPC(p, (short) npcid, "Trùm đã lên 120 ô hành trang rồi");
                                return;}
                         if(p.c.vip > 4 ){ 
                            p.c.levelBag = 4;
                            p.c.maxluggage = 120;
                            p.conn.sendMessageLog("Nâng thành công. Tự thoát game 5s để lưu!");
                            int TimeSeconds = 3 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn); 
                          p.conn.disconnect();
                          return;   
                        }
                         Service.chatNPC(p, (short) npcid, "Từ víp 4 mới được sửa dụng chức năng này!");
                            return;
                               }             
                }
                break;
            }
        case 4:{
             Server.manager.sendTB(p, "Hướng dẫn ", "Xin chào bạn đã đến với sever Tuổi Thơ"
                     +"\nĐể mua exp Víp của liên hệ Admin zalo: 0359538840!"
                                      +"\nBẢNG GIÁ \n" +
                                    "💖 EXP Tuổi Thơ\n" +
                       "              1 vnd = 1 exp\n" +
                       "         - Tính lần mua cộng dồn\n" +
                                    "💖 Level VIP \n" +
                       "      + dưới 50k vnd = 1 lv\n" +
                       "      + trên 100k nvd  = 2 lv\n" +
                       "      + trên 150k vnd  =  5 lv\n" +
                       "      + trên 200k vnd  = 8 lv\n" +
                       "      + trên 1tr vnd  = 12 lv\n" +
                "   - Không tính theo lần mua tính cộng dồn( max là 18 level hút all ) "
                            );
        }
        }
    }
 
  public static void npcKheUoc(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5 ){
                       if (p.c.potential0 >= 10000000){
                       p.conn.sendMessageLog("Trên 10 triệu tiềm năng sức mạnh rồi, chuyển sinh 1 tao chỉ cho đến đây! ");
                       return; 
                       }
                       }
                       if(p.c.nclass == 2 || p.c.nclass == 4|| p.c.nclass == 6){
                       if (p.c.potential3 >= 10000000){
                       p.conn.sendMessageLog("Trên 10 triệu tiềm năng sức mạnh rồi, chuyển sinh 1 tao chỉ cho đến đây!");
                       return; 
                       }
                       }
               if(p.c.exptype == 0)
               {Service.chatNPC(p, (short) npcid, "yêu cầu bật nhận exp");
               return;}
               if (p.c.level < 130) {
                    Service.chatNPC(p, (short) npcid, "yêu cầu lv130");
                       return;}
               if (p.c.quantityItemyTotal(770) < 100){
                       Service.chatNPC(p, (short) npcid, "Cần đạt 100 Viên Thuốc Thần Kỳ ");
                       return;
                       }
                if (p.luong < 10000){
                       Service.chatNPC(p, (short) npcid, "Cần đạt 10000 lượng");
                       return;
                       } if (Util.nextInt(7) == 3){
                        p.c.removeItemBags(770, 100);
                        p.luong -= 10000;
        p.c.upHP (p.c.getMaxHP());
        p.c.upMP((int) p.c.getMaxMP());
        p.c.spoint = p.c.spoint;
        p.c.ppoint = p.c.ppoint;
        p.c.potential0 = p.c.potential0;
        p.c.potential1 = p.c.potential1;
        p.c.potential2 = p.c.potential2;
        p.c.potential3 = p.c.potential3;
                     p.updateExp(Level.getMaxExp(10) - p.c.exp);
                     Service.chatNPC(p, (short) npcid, "Chuyển sinh thành công +11960 sức mạnh. Tự động thoát sau 3 giây");
                     if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    p.c.potential0 += 11960;
                    p.c.chuyenSinh ++;
                }
                if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    p.c.potential3 += 11960;
                    p.c.chuyenSinh ++;
                     }
                    int TimeSeconds = 3 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn); 
                          p.conn.disconnect();
                          return;
                }else{
                Service.chatNPC(p, (short) npcid, "Chuyển sinh thất bại, tặng 20 tiềm năng an ủi!");
                        p.c.removeItemBags(770, 100);
                        p.luong -= 10000;
                        p.c.ppoint += 20; 
                            return; }}
            case 1: {
               // Service.chatNPC(p, (short) npcid, "chào em yêu!");
            }
            if (p.c.isNhanban) {
                p.conn.sendMessageLog("Chức năng này không dành cho phân thân");
                return;
            } else if (p.luong < 500000) {
                p.conn.sendMessageLog("Chuyển phái cần 500k lượng con nhé!");
                return;
            }
            switch (b3) {
                case 0: {
                    if (p.c.nclass == 1) {
                        p.conn.sendMessageLog("Đang ở phái kiếm chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.nclass = 1;
                    p.restSpoint();
                  //  p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 1: {
                    if (p.c.nclass == 2) {
                        p.conn.sendMessageLog("Đang ở phái tiêu chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.nclass = 2;
                    p.restSpoint();
                  //  p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 2: {
                    if (p.c.nclass == 3) {
                        p.conn.sendMessageLog("Đang ở phái kunai chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.nclass = 3;
                    p.restSpoint();
                 //   p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 3: {
                    if (p.c.nclass == 4) {
                        p.conn.sendMessageLog("Đang ở phái cung chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.nclass = 4;
                    p.restSpoint();
                //    p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 4: {
                    if (p.c.nclass == 5) {
                        p.conn.sendMessageLog("Đang ở phái đao chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.nclass = 5;
                    p.restSpoint();
                 //   p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }    
                case 5: {
                    if (p.c.nclass == 6) {
                        p.conn.sendMessageLog("Đang ở phái quạt chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.nclass = 6;
                    p.restSpoint();
                 //   p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
            }
            break;
            case 2: {
                if (p.c.isHuman) {
                p.conn.sendMessageLog("Chức năng này không dành cho chủ thân");
                return;
            } else if (p.luong < 500000) {
                p.conn.sendMessageLog("Chuyển phái cần 500k lượng con nhé!");
                return;
            }
            switch (b3) {
                case 0: {
                    if (p.c.clone.nclass == 1) {
                        p.conn.sendMessageLog("Đang ở phái kiếm chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.clone.nclass = 1;
                    p.restSpoint();
            //        p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 1: {
                    if (p.c.clone.nclass == 2) {
                        p.conn.sendMessageLog("Đang ở phái tiêu chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.clone.nclass = 2;
                    p.restSpoint();
              //      p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 2: {
                    if (p.c.clone.nclass == 3) {
                        p.conn.sendMessageLog("Đang ở phái kunai chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.clone.nclass = 3;
                    p.restSpoint();
             //       p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 3: {
                    if (p.c.clone.nclass == 4) {
                        p.conn.sendMessageLog("Đang ở phái cung chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.clone.nclass = 4;
                    p.restSpoint();
             //       p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
                case 4: {
                    if (p.c.clone.nclass == 5) {
                        p.conn.sendMessageLog("Đang ở phái đao chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.clone.nclass = 5;
                    p.restSpoint();
              //      p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }    
                case 5: {
                    if (p.c.clone.nclass == 6) {
                        p.conn.sendMessageLog("Đang ở phái quạt chuyển cc");
                        return;
                    }
                    if (p.c.get().ItemBody[1] != null) {
                        p.conn.sendMessageLog("Phải tháo vũ khí trước khi chuyển phái");
                        return;
                    }
                    p.c.clone.nclass = 6;
                    p.restSpoint();
               //     p.restPpoint();
                    p.loadSkill();
                    p.upluongMessage(-500000L);
                    Service.chatNPC(p, (short) npcid, "Chuyển phái thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                            return;
                }
            }
            break;
            }
            case 3:{
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
               if(p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5 ){
                       if (p.c.potential0 >= 20000000){
                       p.conn.sendMessageLog("Trên 20 triệu tiềm năng sức mạnh rồi, chuyển sinh 2 tao chỉ cho đến đây! ");
                       return; 
                       }
                       }
                       if(p.c.nclass == 2 || p.c.nclass == 4|| p.c.nclass == 6){
                       if (p.c.potential3 >= 20000000){
                       p.conn.sendMessageLog("Trên 20 triệu tiềm năng sức mạnh rồi, chuyển sinh 2 tao chỉ cho đến đây!");
                       return; 
                       }}
                       if(p.c.nclass == 1 || p.c.nclass == 3|| p.c.nclass == 5){
                       if (p.c.potential0 <= 10000000){
                       p.conn.sendMessageLog("Cần đạt 10 triệu tiềm năng sức mạnh mới chuyển sinh được! ");
                       return; 
                       }
                       }
                       if(p.c.nclass == 2 || p.c.nclass == 4|| p.c.nclass == 6){
                       if (p.c.potential3 <= 10000000){
                       p.conn.sendMessageLog("Cần đạt 10 triệu tiềm năng sức mạnh mới chuyển sinh được!");
                       return; 
                       }
                       }
               if(p.c.exptype == 0)
               {Service.chatNPC(p, (short) npcid, "yêu cầu bật nhận exp");
               return;}
               if (p.c.level < 130) {
                    Service.chatNPC(p, (short) npcid, "yêu cầu lv130");
                       return;}
                if (p.luong < 17000 ){
                       Service.chatNPC(p, (short) npcid, "Cần đạt 17000 lượng , 100 Viên Thuốc Thần Kỳ và 50 Nước diệt khuẩn! ");
                       return;
                       }
                if (p.c.quantityItemyTotal(770) < 100){
                       Service.chatNPC(p, (short) npcid, "Cần đạt 17000 lượng , 100 Viên Thuốc Thần Kỳ và 50 Nước diệt khuẩn! ");
                       return;
                       }
                if ( p.c.quantityItemyTotal(769) < 50 ){
                       Service.chatNPC(p, (short) npcid, "Cần đạt 17000 lượng , 100 Viên Thuốc Thần Kỳ và 50 Nước diệt khuẩn! ");
                       return;
                       } if (Util.nextInt(5) == 3){
                        p.c.removeItemBags(770, 100);
                        p.c.removeItemBags(769, 50);
                        p.luong -= 17000;
        p.c.upHP (p.c.getMaxHP());
        p.c.upMP((int) p.c.getMaxMP());
        p.c.spoint = p.c.spoint;
        p.c.ppoint = p.c.ppoint;
        p.c.potential0 = p.c.potential0;
        p.c.potential1 = p.c.potential1;
        p.c.potential2 = p.c.potential2;
        p.c.potential3 = p.c.potential3;
                     p.updateExp(Level.getMaxExp(10) - p.c.exp);
                     Service.chatNPC(p, (short) npcid, "Người chơi " + p.c.name + " được AD cân nhắc +5980 tiềm năng và chuyển sinh thành công. Tự thoát 3 giây!" );
                     if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    p.c.potential0 += 5980;
                    p.c.chuyenSinh ++;
                }
                if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    p.c.potential3 += 5980;
                    p.c.chuyenSinh ++;
                     }
                    int TimeSeconds = 3 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn); 
                          p.conn.disconnect();
                          return;
                }else{
                Service.chatNPC(p, (short) npcid, "Chuyển sinh thất bại, tặng 100 tiềm năng an ủi!");
                        p.c.removeItemBags(770, 100);
                        p.c.removeItemBags(769, 50);
                        p.luong -= 17000;
                        p.c.ppoint += 100; 
                            return; }}
             case 4:{
                    Service.startYesNoDlg(p, (byte) 13, "Trùm sẽ xoá sạch rương đồ của chính mình ?");
             }
             break;
             case 5:{
                 if(p.c.isHuman){
                 Service.chatNPC(p, (short) npcid, Language.NOT_FOR_CHU_THAN);
                    return;
                 }
                if (p.c.clone.level < 200){                      
                       Service.chatNPC(p, (short) npcid, "Cấp 200 mới chuyển sinh ok!");
                       return;
                       }
                       if(p.c.clone.nclass == 1 || p.c.clone.nclass == 3 || p.c.clone.nclass == 5){
                       if (p.c.clone.potential0 >= 10000000){
                       Service.chatNPC(p, (short) npcid, "Trên 10 triệu tiềm năng sức mạnh rồi!");
                       return; 
                       }
                       }
                       if(p.c.clone.nclass == 2 || p.c.clone.nclass == 4 || p.c.clone.nclass == 6){
                       if (p.c.clone.potential3 >= 10000000){
                       Service.chatNPC(p, (short) npcid, "Trên 10 triệu tiềm năng sức mạnh rồi!");
                       return; 
                       }
                       }
                       if (p.luong < 17000 && p.c.quantityItemyTotal(769) < 50 && p.c.quantityItemyTotal(770) < 100){                      
                       Service.chatNPC(p, (short) npcid, "Cần 17k lượng, 50 nước, 100 thuốc");
                       return;
                       } if (Util.nextInt(10) == 3){
                        p.c.removeItemBags(770, 100);
                        p.c.removeItemBags(769, 50);
                        p.luong -= 70000;
        p.c.upHP (p.c.getMaxHP());
        p.c.upMP((int) p.c.getMaxMP());
        p.c.spoint = p.c.spoint;
        p.c.ppoint = p.c.ppoint;
        p.c.potential0 = p.c.potential0;
        p.c.potential1 = p.c.potential1;
        p.c.potential2 = p.c.potential2;
        p.c.potential3 = p.c.potential3;
                     p.updateExp(Level.getMaxExp(10)-p.c.clone.exp);
                     Service.chatNPC(p, (short) npcid,"Người chơi " + p.c.name + " được AD cân nhắc +5980 tiềm năng và chuyển sinh thành công. Tự thoát 3 giây!" );
                     if (p.c.clone.nclass == 1 || p.c.clone.nclass == 3 || p.c.clone.nclass == 5){
                    p.c.clone.potential0 += 5980;
                    p.c.chuyenSinh ++;
                }
                if (p.c.clone.nclass == 2 || p.c.clone.nclass == 4 || p.c.clone.nclass == 6){
                    p.c.clone.potential3 += 5980;
                    p.c.chuyenSinh ++;
                     }
                    int TimeSeconds = 3 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn); 
                          p.conn.disconnect();
                          return;
                }else{
                Service.chatNPC(p, (short) npcid, "Chuyển sinh thất bại, tặng 100 tiềm năng an ủi!");
                        p.c.removeItemBags(770, 100);
                        p.c.removeItemBags(769, 50);
                        p.luong -= 17000;
                        p.c.clone.ppoint += 100; 
                            return; }}
            case 6: {
                Server.manager.sendTB(p, "Hướng dẫn","Chuyển sinh xong yêu cầu ko tẩy tiềm năng nếu tẩy ad ko chịu trách nhiệm\n"
                        + "đã vào các phái ngoại công như ĐAO KIẾM KUNAI thì các phái đó chỉ lên chuyển phái qua nhau\n"
                        + "còn các phái QUẠT CUNG PHI TIÊU thì cũng chỉ chuyển phái qua nhau \n"
                        + "do chuyển phái số tiềm năng đã cộng sẽ ko đc tẩy lên yêu cầu đọc kĩ\n"
                        + "chuyển nhầm phái hay tẩy ad ko giải puyết.");
                return;
            }
        }
        
        
    }
     
     public static void npcquylao(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {

            case 0: {
                Service.sendInputDialog(p, (short) 24, "Tỷ lệ đổi 1 lượng = 200 xu");
             
            break;
            }
            case 1: {
                Service.sendInputDialog(p, (short) 25, "Tỷ lệ đổi 1 lượng = 200 yên");
        
            break;
             }
            case 2: {
                if (p.c.xu < 500000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500tr xu");
                    break;
                
                    } else{
                         p.c.upxuMessage(-500000000);
                         p.upluongMessage(+100000);
             }break;
        }
    }
     }  
     
 
  
    public static void npcSuKien(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId){
            case 0:{
                switch(b3){
                    case 0:{
                        
                        if(p.luong < 10000){
                            p.conn.sendMessageLog("Bạn không đủ 10k lượng để cho ăn xin ăn xin cũng có giá của ăn xin chứ");
                        }
                        p.c.luongTN =- 10000 ;
                        long luong = 10000;
                        p.c.luongTop += 10000;
                        p.upluongMessage(-10000);
                        break;
                    }
                    case 2:{
                        // nếu muốn tiêu lượng nó cộng thì ++ p.c.luongTN += giá trị ;
                        //trong này nhé
                         Server.manager.sendTB(p, "Top tiêu lượng", Rank.getStringBXH(4));
                        break;
                    }
                }
                break;
            }
        }
    }
     public static void npcdichchuyen(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch(menuId){
            case 0: {             
                switch (b3){
                    case 0: {
                        
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể dịch chuyển qua hành tinh kamado khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        if(p.c.quantityItemyTotal(993) < 5){
                    p.conn.sendMessageLog("muốn đi sangg hành tinh kamado thì đưa tao 5 miếng dưa hấu tao sẽ giúp mày sang đấy");
                    return;
                }
                    
                
                     else{
                        p.c.removeItemBags(993, 5);
                        }
                        p.c.tileMap.leave(p);
                        Map map = Server.maps[169];
                        byte k;
                        for (k = 0; k < map.area.length; k++) {
                            if (map.area[k].numplayers < map.template.maxplayers) {
                                map.area[k].EnterMap0(p.c);
                                break;
                            }
                        }
                        p.endLoad(true);
                        break;
                    }
                }
            }
        }
     }
     public static void npcuocngocrong(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {         
            case 0: {
                switch (b3) {
                    case 0: {
                    if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 50000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 50tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 90) {
                         p.c.yenTN =- 50000000 ;
                         p.c.upyenMessage(-50000000);
                         p.c.removeItemBags(996, 200);
                         p.c.removeItemBags(997, 200);
                         p.c.removeItemBags(998, 200);
                         p.c.removeItemBags(999, 200);
                         p.c.removeItemBags(1000, 200);
                         p.c.removeItemBags(1001, 200);
                         p.c.removeItemBags(1002, 200);
                         p.conn.sendMessageLog("tao lạy mày 90% còn để xịt xin mỗi loại ngọc rồng 200 viên lần sau cố gắng hơn");
                         Server.manager.chatKTG("người chơi " + p.c.name + "ước được nhiều lượng nhưng đen quá nên xịt! " );
                         return;
                    }else{
                        p.c.yenTN =- 50000000 ;
                        p.c.upyenMessage(-50000000);
                        p.c.removeItemBags(996, 999);
                        p.c.removeItemBags(997, 999);
                        p.c.removeItemBags(998, 999);
                        p.c.removeItemBags(999, 999);
                        p.c.removeItemBags(1000, 999);
                        p.c.removeItemBags(1001, 999);
                        p.c.removeItemBags(1002, 999);
                        p.upluongMessage(5000000);
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước nhiều lượng thành công phát tài rồi! " );
                    }
                    }
                    break;
            }
                    
                    case 1: {
                   if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 50000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 50tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 90) {
                         p.c.yenTN =- 50000000 ;
                         p.c.upyenMessage(-50000000);
                         p.c.removeItemBags(996, 200);
                         p.c.removeItemBags(997, 200);
                         p.c.removeItemBags(998, 200);
                         p.c.removeItemBags(999, 200);
                         p.c.removeItemBags(1000, 200);
                         p.c.removeItemBags(1001, 200);
                         p.c.removeItemBags(1002, 200);
                         p.conn.sendMessageLog("tao lạy mày 90% còn để xịt xin mỗi loại ngọc rồng 200 viên lần sau cố gắng hơn");
                         Server.manager.chatKTG("người chơi " + p.c.name + "ước được nhiều lượng nhưng đen quá nên xịt! " );
                         return;
                    }else{
                        p.c.yenTN =- 50000000 ;
                        p.c.upyenMessage(-50000000);
                        p.c.removeItemBags(996, 999);
                        p.c.removeItemBags(997, 999);
                        p.c.removeItemBags(998, 999);
                        p.c.removeItemBags(999, 999);
                        p.c.removeItemBags(1000, 999);
                        p.c.removeItemBags(1001, 999);
                        p.c.removeItemBags(1002, 999);
                        p.c.upxuMessage(20000000);
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước nhiều lượng thành công phát tài rồi! " );
                    }
                    }
                    break;
            
            }
                    case 2: {
                    if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 500000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 1) {
                         p.c.yenTN =- 500000000 ;
                         p.c.upyenMessage(-500000000);
                         p.c.removeItemBags(996, 2);
                         p.c.removeItemBags(997, 2);
                         p.c.removeItemBags(998, 2);
                         p.c.removeItemBags(999, 2);
                         p.c.removeItemBags(1000, 2);
                         p.c.removeItemBags(1001, 2);
                         p.c.removeItemBags(1002, 2);
                         p.conn.sendMessageLog("điều ước của con hơi khó cho ta thêm ít thời gian suy nghĩ");
                         
                         return;
                    }else{
                        p.c.yenTN =- 500000000 ;
                        p.c.upyenMessage(-500000000);
                        p.c.removeItemBags(996, 9);
                        p.c.removeItemBags(997, 9);
                        p.c.removeItemBags(998, 9);
                        p.c.removeItemBags(999, 9);
                        p.c.removeItemBags(1000, 9);
                        p.c.removeItemBags(1001, 9);
                        p.c.removeItemBags(1002, 9);
                       
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước đẹp trai nhất sever nhưng do xấu quá nên chịu nhá bó tay :))! " );
                    }
                    }
                    break;
            
            }
                    case 3: {
                    if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 500000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 1) {
                         p.c.yenTN =- 500000000 ;
                         p.c.upyenMessage(-500000000);
                         p.c.removeItemBags(996, 2);
                         p.c.removeItemBags(997, 2);
                         p.c.removeItemBags(998, 2);
                         p.c.removeItemBags(999, 2);
                         p.c.removeItemBags(1000, 2);
                         p.c.removeItemBags(1001, 2);
                         p.c.removeItemBags(1002, 2);
                         p.conn.sendMessageLog("điều ước của con hơi khó cho ta thêm ít thời gian suy nghĩ");
                         
                         return;
                    }else{
                        p.c.yenTN =- 500000000 ;
                        p.c.upyenMessage(-500000000);
                        p.c.removeItemBags(996, 9);
                        p.c.removeItemBags(997, 9);
                        p.c.removeItemBags(998, 9);
                        p.c.removeItemBags(999, 9);
                        p.c.removeItemBags(1000, 9);
                        p.c.removeItemBags(1001, 9);
                        p.c.removeItemBags(1002, 9);
                       
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước có người yêu nhưng do xâú quá chó nó yêu ! " );
                    }
                    }
                    break;
            
                    }
                    case 4: {
                    if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 500000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 1) {
                         p.c.yenTN =- 500000000 ;
                         p.c.upyenMessage(-500000000);
                         p.c.removeItemBags(996, 2);
                         p.c.removeItemBags(997, 2);
                         p.c.removeItemBags(998, 2);
                         p.c.removeItemBags(999, 2);
                         p.c.removeItemBags(1000, 2);
                         p.c.removeItemBags(1001, 2);
                         p.c.removeItemBags(1002, 2);
                         p.conn.sendMessageLog("điều ước của con hơi khó cho ta thêm ít thời gian suy nghĩ");
                         
                         return;
                    }else{
                        p.c.yenTN =- 500000000 ;
                        p.c.upyenMessage(-500000000);
                        p.c.removeItemBags(996, 9);
                        p.c.removeItemBags(997, 9);
                        p.c.removeItemBags(998, 9);
                        p.c.removeItemBags(999, 9);
                        p.c.removeItemBags(1000, 9);
                        p.c.removeItemBags(1001, 9);
                        p.c.removeItemBags(1002, 9);
                       
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước có quyền admin nhưng ad bảo đéo thích cho :))! " );
                    }
                    }
                    break;
            
            }
                    case 5: {
                   if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 500000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 1) {
                         p.c.yenTN =- 500000000 ;
                         p.c.upyenMessage(-500000000);
                         p.c.removeItemBags(996, 2);
                         p.c.removeItemBags(997, 2);
                         p.c.removeItemBags(998, 2);
                         p.c.removeItemBags(999, 2);
                         p.c.removeItemBags(1000, 2);
                         p.c.removeItemBags(1001, 2);
                         p.c.removeItemBags(1002, 2);
                         p.conn.sendMessageLog("điều ước của con hơi khó cho ta thêm ít thời gian suy nghĩ");
                         
                         return;
                    }else{
                        p.c.yenTN =- 500000000 ;
                        p.c.upyenMessage(-500000000);
                        p.c.removeItemBags(996, 9);
                        p.c.removeItemBags(997, 9);
                        p.c.removeItemBags(998, 9);
                        p.c.removeItemBags(999, 9);
                        p.c.removeItemBags(1000, 9);
                        p.c.removeItemBags(1001, 9);
                        p.c.removeItemBags(1002, 9);
                       
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước full đồ mua, thầy huấn bảo rồi không cày mà muốn full đồ chỉ có ..... :))! " );
                    }
                    }
                    break;
            
            }
                    case 6: {
                    if(p.c.quantityItemyTotal(996) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 999){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 500000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 1) {
                         p.c.yenTN =- 500000000 ;
                         p.c.upyenMessage(-500000000);
                         p.c.removeItemBags(996, 2);
                         p.c.removeItemBags(997, 2);
                         p.c.removeItemBags(998, 2);
                         p.c.removeItemBags(999, 2);
                         p.c.removeItemBags(1000, 2);
                         p.c.removeItemBags(1001, 2);
                         p.c.removeItemBags(1002, 2);
                         p.conn.sendMessageLog("điều ước của con hơi khó cho ta thêm ít thời gian suy nghĩ");
                         
                         return;
                    }else{
                        p.c.yenTN =- 500000000 ;
                        p.c.upyenMessage(-500000000);
                        p.c.removeItemBags(996, 9);
                        p.c.removeItemBags(997, 9);
                        p.c.removeItemBags(998, 9);
                        p.c.removeItemBags(999, 9);
                        p.c.removeItemBags(1000, 9);
                        p.c.removeItemBags(1001, 9);
                        p.c.removeItemBags(1002, 9);
                       
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước 2 tỷ coin ad bảo không nạp thì có cái nịt nha :))! " );
                    }
                    }
                    break;
            
            }
                     case 7: {
                    if(p.c.quantityItemyTotal(996) < 300){
                    p.conn.sendMessageLog("bạn không đủ 300 ngọc rồng 1 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(997) < 300){
                    p.conn.sendMessageLog("bạn không đủ 300 ngọc rồng 2 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(998) < 300){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 3 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(999) < 300){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 4 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1000) < 300){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 5 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1001) < 300){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 6 sao");
                    return;
                }
                    if(p.c.quantityItemyTotal(1002) < 300){
                    p.conn.sendMessageLog("bạn không đủ 999 ngọc rồng 7 sao");
                    return;
                }
                    else if (p.c.yen < 50000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 50tr  yên");
                    break;

                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 50) {
                         p.c.yenTN =- 50000000 ;
                         p.c.upyenMessage(-50000000);
                         p.c.removeItemBags(996, 200);
                         p.c.removeItemBags(997, 200);
                         p.c.removeItemBags(998, 200);
                         p.c.removeItemBags(999, 200);
                         p.c.removeItemBags(1000, 200);
                         p.c.removeItemBags(1001, 200);
                         p.c.removeItemBags(1002, 200);
                         p.conn.sendMessageLog("điều ước của con hơi khó cho ta thêm ít thời gian suy nghĩ");
                         
                         return;
                    }else{
                        p.c.yenTN =- 50000000 ;
                        p.c.upyenMessage(-50000000);
                        p.c.removeItemBags(996, 300);
                        p.c.removeItemBags(997, 300);
                        p.c.removeItemBags(998, 300);
                        p.c.removeItemBags(999, 300);
                        p.c.removeItemBags(1000, 300);
                        p.c.removeItemBags(1001, 300);
                        p.c.removeItemBags(1002, 300);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(1003));
                 Service.chatNPC(p, (short) npcid,"ước thành công điều ước đã được thực hiện");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã ước thành công đá ngũ sắc :))! " );
                    }
                    }
                    break;
            
            }
                    case 8: {
                    Server.manager.sendTB(p, "Điều Kiện để ước ngọc rồng", "người chơi cần phải đánh quái trong map này để kiếm ngọc rồng từ 1 tới 7 sao"
                            + "\n>điều ước<"
                            + "\n-ước đc nhiều lượng sẽ nhận đc 5m lượng"
                            + "\n-ước nhiều xu sẽ được 20m xu"
                            + "\n-ước đẹp trai nhất sever thì sẽ nhận được 1 mặt nạ vip"
                            + "\n-ước có người yêu bạn sẽ có 1 người yêu đi bên cạnh và chiến đấu cùng mình"
                            + "\n-ước quyền admin bạn sẽ được quyền khóa tk bất kỳ ai trong server"
                            + "\n-ước full đồ mua bạn sẽ nhận được full đồ đăng có trên wep shop"
                            + "\n-ước 2 tỷ coin thì bạn sẽ nhận được 2 tỷ coin có thể lên wep để xem số coin"
                            + "\n-ước đá ngũ sắc sẽ nhận được 1 viên đá ngũ sắc"
                            + "\n-điều ước càng xịn thì độ khó càng cao"
                            + "\n-CHÚC CÁC BẠN THÀNH CÔNG"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                           + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                           + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n"
                            + "\n- ADMIN MUÔN ĐỜI BỊP TIN ADMIN CHỈ CÓ BÁN NHÀ MÀ ĂN"
                            );
                    break;
                }
            }
               break;
        }    
        }
    }
   
    public static void npcPhoBan(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch(menuId){
            case 0: {             
                switch (b3){
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, (short)npcid, Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if(TuTien.tuTien == null) {
                            Service.chatNPC(p, (short)npcid, "Bây giờ chưa phải thời gian để tu luyện.");
                            return;
                        }
                        if(p.c.level < 140) {
                            Service.chatNPC(p, (short)npcid, "Tối thiểu 140 cấp.");
                            return;
                        }
                         
                        Map ma = Manager.getMapid(TuTien.tuTien.map[0].id);
                        for (TileMap area : ma.area) {
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap0(p.c);
                                return;
                            }
                        }
                        return;
                    }
                   
                       case 1: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào làng Lá khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        p.c.tileMap.leave(p);
                        Map map = Server.maps[165];
                        byte k;
                        for (k = 0; k < map.area.length; k++) {
                            if (map.area[k].numplayers < map.template.maxplayers) {
                                map.area[k].EnterMap0(p.c);
                                break;
                            }
                        }
                        p.endLoad(true);
                        break;
                    }
                }
            }
             case 1: {             
                switch (b3){
                      case 0: {
                 if (p.c.chuyenSinh < 10) {
                   p.conn.sendMessageLog( "yêu cầu chuyển sinh trên 10 lần");
                    return;
                } 
                   if (p.c.expCS < 2000000000){
                    p.conn.sendMessageLog("bạn không đủ 2 tỷ exp cải lão");
                    return;
                }
                if(p.luong < 500000){
                    p.conn.sendMessageLog("con phải có trên 500k lượng ");
                    return;
                     }
                if(p.c.potential0 < 100000){
                    p.conn.sendMessageLog("con cần 100k tiềm năng sưc mạnh để cải lão");
                    return;
                     } else {
                    p.c.cailao ++;
                    p.coin += 100000;
                    p.upluongMessage(-500000);
                    p.c.potential0 -= 100000;
                    p.c.expCS -= 2000000000;
                    p.loadPpoint();
                   
                    
               p.conn.sendMessageLog("Cải Lão thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                           }
                break;
                    }
                         case 1: {
                 if (p.c.chuyenSinh < 10) {
                    p.conn.sendMessageLog( "yêu cầu chuyển sinh trên 10 lần");
                    return;
                } 
                 if (p.c.expCS < 2000000000){
                    p.conn.sendMessageLog("bạn không đủ 2 tỷ exp cải lão");
                    return;
                }
                if(p.luong < 500000){
                    p.conn.sendMessageLog("con phải có trên 500k lượng ");
                    return;
                     }
                if(p.c.potential1 < 100000){
                    p.conn.sendMessageLog("con cần 100k tiềm năng thân pháp để cải lão");
                    return;
                     } else {
                    p.coin += 100000;
                    p.upluongMessage(-500000);
                    p.c.cailao ++;
                    p.c.potential1 -= 100000;
                    p.c.expCS -= 2000000000;
                    p.loadPpoint();
                   
                    
                p.conn.sendMessageLog("Cải Lão thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                           }
                break;
                    }
                        case 2: {
                 if (p.c.chuyenSinh < 10) {
                    p.conn.sendMessageLog( "yêu cầu chuyển sinh trên 10 lần");
                    return;
                } 
                 if (p.c.expCS < 2000000000){
                    p.conn.sendMessageLog("bạn không đủ 2 tỷ exp cải lão");
                    return;
                }
               if(p.luong < 500000){
                    p.conn.sendMessageLog("con phải có trên 500k lượng ");
                    return;
                     }
                if(p.c.potential3 < 100000){
                    p.conn.sendMessageLog("con cần 100k tiềm năng Thể Lực để cải lão");
                    return;
                     } else {
                    p.coin += 100000;
                    p.upluongMessage(-500000);
                    p.c.cailao ++;
                    p.c.potential3 -= 100000;
                    p.c.expCS -= 2000000000;
                    p.loadPpoint();
                   
                    
                p.conn.sendMessageLog("Cải Lão thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                           }
                break;
                    }
                         case 3: {
                 if (p.c.chuyenSinh < 10) {
                    p.conn.sendMessageLog( "yêu cầu chuyển sinh trên 10 lần");
                    return;
                } 
                 if (p.c.expCS < 2000000000){
                    p.conn.sendMessageLog("bạn không đủ 2 tỷ exp cải lão");
                    return;
                }
                if(p.luong < 500000){
                    p.conn.sendMessageLog("con phải có trên 500k lượng ");
                    return;
                     }
                if(p.c.potential3 < 100000){
                    p.conn.sendMessageLog("con cần 100k tiềm năng Chakra để cải lão");
                    return;
                     } else {
                    p.coin += 100000;
                    p.upluongMessage(-500000);
                    p.c.cailao ++;
                    p.c.potential3 -= 100000;
                    p.c.expCS -= 2000000000;
                    p.loadPpoint();
                   
                    
                p.conn.sendMessageLog("Cải Lão thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds =5 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
                           }
                break;
                    }
                    } 
                }
        }
    }
    
    public static void npcthiensuwhis(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch(menuId){
            case 0: { // mặt nạ
                ItemTemplate data;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.ItemBody[11] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy đeo mặt nạ vào người trước rồi nâng cấp nhé.");
                    return;
                }
                if (p.c.ItemBody[11].upgrade >= 16) {
                    Service.chatNPC(p, (short) npcid, " đã đạt cấp tối đa ");
                    return;
                }
                data = ItemTemplate.ItemTemplateId(p.c.ItemBody[11].id);
                Service.startYesNoDlg(p, (byte) 11, "Bạn có muốn nâng cấp mặt nạ " + data.name + " cấp " + (p.c.ItemBody[11].upgrade + 1) + " với " + GameSrc.da[p.c.ItemBody[11].upgrade] + " chuyển tinh thạch và " + GameSrc.coinup[p.c.ItemBody[11].upgrade] + " lượng tỷ lệ thành công là " + GameSrc.tile[p.c.ItemBody[11].upgrade] + "% không?");
                break;
            }
              case 1: { // bíkip
                ItemTemplate data;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.ItemBody[15] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy Mặc Bí Kíp vào để nâng cấp.");
                    return;
                }
                if (p.c.ItemBody[15].upgrade >= 16) {
                    Service.chatNPC(p, (short) npcid, " đã đạt cấp tối đa ");
                    return;
                }
                data = ItemTemplate.ItemTemplateId(p.c.ItemBody[15].id);
                Service.startYesNoDlg(p, (byte) 15, "Bạn có muốn nâng cấp  " + data.name + " cấp " + (p.c.ItemBody[15].upgrade + 1) + " với " + GameSrc.da[p.c.ItemBody[15].upgrade] + " chuyển tinh thạch và " + GameSrc.coinup[p.c.ItemBody[15].upgrade] + " lượng tỷ lệ thành công là " + GameSrc.tile[p.c.ItemBody[15].upgrade] + "% không?");
                break;
            }
               case 2: { //yoroi
                ItemTemplate data;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.ItemBody[12] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy Mặc Yoroi vào để nâng cấp.");
                    return;
                }
                if (p.c.ItemBody[12].upgrade >= 16) {
                    Service.chatNPC(p, (short) npcid, " đã đạt cấp tối đa ");
                    return;
                }
                data = ItemTemplate.ItemTemplateId(p.c.ItemBody[12].id);
                Service.startYesNoDlg(p, (byte) 12, "Bạn có muốn nâng cấp  " + data.name + " cấp " + (p.c.ItemBody[12].upgrade + 1) + " với " + GameSrc.da[p.c.ItemBody[12].upgrade] + " chuyển tinh thạch và " + GameSrc.coinup[p.c.ItemBody[12].upgrade] + " lượng tỷ lệ thành công là " + GameSrc.tile[p.c.ItemBody[12].upgrade] + "% không?");
                break;
            }
                case 3: { //nangpet
                ItemTemplate data;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.ItemBody[10] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy Mặc Pet vào để nâng cấp.");
                    return;
                }
                if (p.c.ItemBody[10].upgrade >= 16) {
                    Service.chatNPC(p, (short) npcid, " đã đạt cấp tối đa ");
                    return;
                }
                data = ItemTemplate.ItemTemplateId(p.c.ItemBody[10].id);
                Service.startYesNoDlg(p, (byte) 10, "Bạn có muốn nâng cấp pet " + data.name + " cấp " + (p.c.ItemBody[10].upgrade + 1) + " với " + GameSrc.da[p.c.ItemBody[10].upgrade] + " chuyển tinh thạch và " + GameSrc.coinup[p.c.ItemBody[10].upgrade] + " lượng tỷ lệ thành công là " + GameSrc.tile[p.c.ItemBody[10].upgrade] + "% không?");
                break;
            }
                 case 4: {// mắt
                ItemTemplate data;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.ItemBody[14] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy đeo mắt vào người trước rồi nâng cấp nhé.");
                    return;
                }

                if (p.c.ItemBody[14].upgrade >= 10) {
                    Service.chatNPC(p, (short) npcid, "Mắt của con đã đạt cấp tối đa");
                    return;
                }

                if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                    Service.chatNPC(p, (short) npcid, "Con chưa đủ điểm danh vọng để thực hiện nâng cấp");
                    return;
                }

                data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                Service.startYesNoDlg(p, (byte) 1, "Bạn có muốn nâng cấp " + data.name + " với " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " yên hoặc xu và " + GameSrc.goldUpMat[p.c.ItemBody[14].upgrade] + " lượng với tỷ lệ thành công là " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] * 2 + "% không?");
                break;
            }
                 case 5: {// ntgt
               ItemTemplate data;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.ItemBody[13] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy Mặc ntgt vào để nâng cấp.");
                    return;
                }
                if (p.c.ItemBody[13].upgrade >= 16) {
                    Service.chatNPC(p, (short) npcid, " đã đạt cấp tối đa ");
                    return;
                }
                data = ItemTemplate.ItemTemplateId(p.c.ItemBody[13].id);
                Service.startYesNoDlg(p, (byte) 16, "Bạn có muốn nâng cấp ntgt " + data.name + " cấp " + (p.c.ItemBody[13].upgrade + 1) + " với " + GameSrc.da[p.c.ItemBody[13].upgrade] + " chuyển tinh thạch và " + GameSrc.coinup[p.c.ItemBody[13].upgrade] + " lượng tỷ lệ thành công là " + GameSrc.tile[p.c.ItemBody[13].upgrade] + "% không?");
                break;
            }
                  case 6: { //hành trang
                if (p.c.maxluggage >= 120) {
                    p.conn.sendMessageLog("Bạn chỉ có thể nâng tối đa 120 ô hành trang");
                    return;
                }
                 if (p.c.levelBag < 4) {
                    p.conn.sendMessageLog("con hãy cắn túi vải cấp 4 rồi đến gặp ta");
                    return;
                }
                if (p.luong < 10000) {
                    p.conn.sendMessageLog("Bạn Cần 10000 Lượng");
                    return;
                } else {
                    p.c.maxluggage += 5;
                    p.upluongMessage(-10000L);
                    p.conn.sendMessageLog("Hành trang đã mở thêm 6 ô, Số Ô Hành Trang Của Bạn Là " + p.c.maxluggage + " Vui lòng thoát game vào lại để update hành trang ");
                   Service.chatNPC(p, (short) npcid, "nâng hàng trang thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds = 5;
                    while (TimeSeconds > 0) {
                        TimeSeconds--;
                        Thread.sleep(1000);
                    }
                    Client.gI().kickSession(p.conn);
                    break;
                }
              
        }
    }
   } 
     
    public static void BXH(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
           case 0: {
                Server.manager.sendTB(p, "Top Đại gia", Rank.getStringBXH(0));
                break;
            }
            case 1: {
                Server.manager.sendTB(p, "Top Cao Thủ", Rank.getStringBXH(1));
                break;
            }
              case 2: {
                Server.manager.sendTB(p, "Top Gia Tộc", Rank.getStringBXH(2));
                break;
            }
                case 3: {
                Server.manager.sendTB(p, "Top Hang Động", Rank.getStringBXH(3));
                break;
            }
                  case 4: {
                Server.manager.sendTB(p, "Top Tiêu Lượng", Rank.getStringBXH(4));
                break;
            }
                     case 5: {
                Server.manager.sendTB(p, "Top Dame", Rank.getStringBXH(5));
                break;
            }
                     case 6: {
                Server.manager.sendTB(p, "Top Tiềm Năng", Rank.getStringBXH(6));
                break;
            }
                      case 7: {
                Server.manager.sendTB(p, "Top Chuyển Sinh", Rank.getStringBXH(7));
                break;
            }
                      case 8: {
                Server.manager.sendTB(p, "Top nap", Rank.getStringBXH(8));
                break;
            }
                      case 9: {
                Server.manager.sendTB(p, "Top cần thủ", Rank.getStringBXH(9));
                break;
            }
                      case 10: {
                Server.manager.sendTB(p, "Top bợm nhậu", Rank.getStringBXH(10));
                break;
            }
                       case 11: {
                Server.manager.sendTB(p, "Top cắn đan", Rank.getStringBXH(11));
                break;
            }
                 case 12: {
                            String a = "";
                            if(Rank.bxhBossTuanLoc.isEmpty()) {
                                a = "Chưa có thông tin.";
                            }
                            for(Rank.Entry3 item : Rank.bxhBossTuanLoc) {
                                a += item.index +". "+item.name+": "+item.point+" điểm\n";
                            }
                            Server.manager.sendTB(p, "BXH Diệt Boss", a);
                            break;
                        }

            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    
    }   
    public static void npcmathan(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                    case 0:{
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemcauca < 700) {
                    Service.chatNPC(p, (short) npcid, "mày chưa đủ 700 điểm câu cá thì tới đây làm gì\n mau đi câu cá đi\n lượn");   
                    break;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ 1  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemcauca >= 700) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(968));
                    Service.chatNPC(p, (short) npcid, "bạn đã đổi 700 điểm câu cá để lấy quần gấu bông");   
                    p.c.diemcauca -= 700;
                    break;
             }
             }
                    case 1:{
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemcauca < 800) {
                    Service.chatNPC(p, (short) npcid, "mày chưa đủ 800 điểm câu cá thì tới đây làm gì\n mau đi câu cá đi\n lượn");   
                    break;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ 1  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemcauca >= 800) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(969));
                    Service.chatNPC(p, (short) npcid, "bạn đã đổi 800 điểm câu cá để lấy áo gấu bông");   
                    p.c.diemcauca -= 800;
                    break;
             }
             }
                    case 2:{
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemcauca < 900) {
                    Service.chatNPC(p, (short) npcid, "mày chưa đủ 900 điểm câu cá thì tới đây làm gì\n mau đi câu cá đi\n lượn");   
                    break;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ 1  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemcauca >= 900) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(970));
                    Service.chatNPC(p, (short) npcid, "bạn đã đổi 900 điểm câu cá để lấy mặt nạ gấu bông");   
                    p.c.diemcauca -= 900;
                    break;
             }
             }
                    case 3:{
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if(p.c.diemcauca < 1000) {
                    Service.chatNPC(p, (short) npcid, "mày chưa đủ 1000 điểm câu cá thì tới đây làm gì\n mau đi câu cá đi\n lượn");   
                    break;
                }
                if(p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ 1  chỗ trống để nhận quà");
                    return;
                }
                if(p.c.diemcauca >= 1000) {
                    p.c.addItemBag(false, ItemTemplate.itemDefault(971));
                    Service.chatNPC(p, (short) npcid, "bạn đã đổi 1000 điểm câu cá để lấy cây kẹo mật");   
                    p.c.diemcauca -= 1000;
                    break;
             }
             }
                    case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "mày cần đủ điểm câu cá để đổi đồ"
                        + "\nMỗi 1 lần câu cá ở làng chài sẽ ngẫu nhiên đc 1 điểm câu cá\n"
                        + "đủ 700 điểm sẽ đổi được quần gấu bông\n"
                        + "đủ 800 điểm sẽ đổi được áo gấu bông\n"
                        + "đủ 900 điểm sẽ đổi được mặt nạ gấu bông\n"
                        + "đủ 1000 điểm sẽ đổi được cây kẹo mật \n"
                        + "Chúc mày Sớm Trở Thành Trùm câu cá!");
                break;
            }
        }
       break;         
    }
        }
        }
   public static void npcNuoiRong (Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId){
            case 0: {
                switch (b3) {
                       case 0: {
                        if (p.c.quantityItemyTotal(601) < 1) {
                            
                            Service.chatNPC(p, (short) npcid, "Tối thiểu mày phải có 1 quả trứng Hải Mã Chứ?");
                            break;
                        } else if (p.luong < 100000) {
                            Service.chatNPC(p, (short) npcid, "Cần 100000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl <  70) {
                                Service.chatNPC(p, (short) npcid, "Trứng này bị hỏng rồi không dùng được");
                                p.upluongMessage(-50000);
                                p.c.removeItemBags(601, 1);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn đã ấp được Hải mã Con");
                                
                                 final Item itemup = ItemTemplate.itemDefault(584);
                                 
                                 itemup.upgrade = 12;
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(601, 1);
                                p.c.addItemBag(false, itemup);
                               Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Ấp Được Hải Mã Con! ");
                               
                            }
                        } 
                   break;
                    } 
                    case 1: {
                        if (p.c.quantityItemyTotal(596) < 1) {
                           Service.chatNPC(p, (short) npcid, "Tối thiểu mày phải có 1 quả trứng Dị Long Chứ?");
                            break;
                        } else if (p.luong < 100000) {
                            Service.chatNPC(p, (short) npcid, "Cần 100000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl < 70) {
                               Service.chatNPC(p, (short) npcid, "Trứng này bị hỏng rồi không dùng được");
                                p.upluongMessage(-50000);
                                p.c.removeItemBags(596, 1);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn đã ấp được Dị Long Con");
                                final Item itemup = ItemTemplate.itemDefault(587);
                                
                                 itemup.upgrade = 12;
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(596, 1);
                                p.c.addItemBag(false, itemup);
                                Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Ấp Được Dị Long Con! ");
                                break;
                            }
                        }
                    }
                     case 2: {
                        if (p.c.quantityItemyTotal(967) < 1) {
                            
                            Service.chatNPC(p, (short) npcid, "Tối thiểu mày phải có 1 quả trứng Khủng long Chứ?");
                            break;
                        } else if (p.luong < 100000) {
                            Service.chatNPC(p, (short) npcid, "Cần 100000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl <  70) {
                                Service.chatNPC(p, (short) npcid, "Trứng này bị hỏng rồi không dùng được");
                                p.upluongMessage(-50000);
                                p.c.removeItemBags(967, 1);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn đã ấp được khủng long Con");
                                
                                 final Item itemup = ItemTemplate.itemDefault(772);
                                 
                                 itemup.upgrade = 12;
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(967, 1);
                                p.c.addItemBag(false, itemup);
                               Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Ấp Được khủng long Con! ");
                               
                            }
                        } 
                   break;
                    } 
                }
            }
              break;

            case 1: {
                switch (b3) {
                       case 0: {
                        if (p.c.quantityItemyTotal(584) < 10) {
                            
                            Service.chatNPC(p, (short) npcid, "Cần 10 con Hải Mã Con!");
                            break;
                        } else if (p.luong < 200000) {
                            Service.chatNPC(p, (short) npcid, "Cần 200000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl <  80) {
                                Service.chatNPC(p, (short) npcid, "Tiến Hóa Thất bại");
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(584, 10);
                                break;
                            } else {
                                Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Tiến Hóa Hải Mã Con Lên Hải Mã Thiếu Niên! ");
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn Đã Tiến Hóa Thành Công. Nhận Hải Mã Thiếu Niên");
                                 final Item itemup = ItemTemplate.itemDefault(585);
                                 
                                 itemup.upgrade = 14;
                                p.upluongMessage(-200000);
                                p.c.removeItemBags(584, 10);
                                p.c.addItemBag(false, itemup);
                               
                               
                            }
                        } 
                   break;
                    } 
                    case 1: {
                        if (p.c.quantityItemyTotal(585) < 10) {
                           Service.chatNPC(p, (short) npcid, "Cần 10 Hải Mã Thiếu Niên!");
                            break;
                        } else if (p.luong < 200000) {
                            Service.chatNPC(p, (short) npcid, "Cần 200000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl < 90) {
                               Service.chatNPC(p, (short) npcid, "Tiến Hóa Thất Bại");
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(585, 10);
                                break;
                            } else {
                                Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Tiến Hóa Hải Mã Thiếu Niên Lên Hải Mã Trưởng Thành! ");
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn Đã Tiến Hóa Thành Công. Nhận Hải Mã Trưởng Thành");
                                final Item itemup = ItemTemplate.itemDefault(586);
                                
                                 itemup.upgrade = 16;
                                p.upluongMessage(-200000);
                                p.c.removeItemBags(585, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                }
            }
              break;
            case 2: {
                switch (b3) {
                       case 0: {
                        if (p.c.quantityItemyTotal(587) < 10) {
                            
                            Service.chatNPC(p, (short) npcid, "Cần 10 con Dị Long Con!");
                            break;
                        } else if (p.luong < 200000) {
                            Service.chatNPC(p, (short) npcid, "Cần 200000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl <  80) {
                                Service.chatNPC(p, (short) npcid, "Tiến Hóa Thất bại");
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(587, 10);
                                break;
                            } else {
                                 
                                 Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Tiến Hóa Dị Long Con Lên Dị Long Thiếu Niên! ");
                                 Service.chatNPC(p, (short) npcid, "Chúc mừng bạn Đã Tiến Hóa Thành Công. Nhận Dị Long Thiếu Niên");
                                 final Item itemup = ItemTemplate.itemDefault(588);
                                 
                                 itemup.upgrade = 14;
                                p.upluongMessage(-200000);
                                p.c.removeItemBags(587, 10);
                                p.c.addItemBag(false, itemup);
                               
                               
                            }
                        } 
                   break;
                    } 
                    case 1: {
                        if (p.c.quantityItemyTotal(588) < 10) {
                           Service.chatNPC(p, (short) npcid, "Cần 10 Dị Long Thiếu Niên!");
                            break;
                        } else if (p.luong < 200000) {
                            Service.chatNPC(p, (short) npcid, "Cần 200000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl < 90) {
                               Service.chatNPC(p, (short) npcid, "Tiến Hóa Thất Bại");
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(588, 10);
                                break;
                            } else {
                                Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Tiến Hóa Dị Long Thiếu Niên Con Lên Dị Long Trưởng Thành! ");
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn đã ấp được Dị Long Trưởng Thành");
                                final Item itemup = ItemTemplate.itemDefault(589);
                                
                                 itemup.upgrade = 16;
                                p.upluongMessage(-200000);
                                p.c.removeItemBags(588, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                }
            } 
                    
                    
                        case 3: {
                switch (b3) {
                       case 0: {
                        if (p.c.quantityItemyTotal(772) < 10) {
                            
                            Service.chatNPC(p, (short) npcid, "Cần 10 con khủng long Con!");
                            break;
                        } else if (p.luong < 200000) {
                            Service.chatNPC(p, (short) npcid, "Cần 200000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl <  80) {
                                Service.chatNPC(p, (short) npcid, "Tiến Hóa Thất bại");
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(772, 10);
                                break;
                            } else {
                                 
                                 Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Tiến Hóa khủng long Con Lên khủng long Thiếu Niên! ");
                                 Service.chatNPC(p, (short) npcid, "Chúc mừng bạn Đã Tiến Hóa Thành Công. Nhận khủng Long Thiếu Niên");
                                 final Item itemup = ItemTemplate.itemDefault(773);
                                 
                                 itemup.upgrade = 14;
                                p.upluongMessage(-200000);
                                p.c.removeItemBags(772, 10);
                                p.c.addItemBag(false, itemup);
                               
                               
                            }
                        } 
                   break;
                    } 
                    case 1: {
                        if (p.c.quantityItemyTotal(773) < 10) {
                           Service.chatNPC(p, (short) npcid, "Cần 10 khủng Long Thiếu Niên!");
                            break;
                        } else if (p.luong < 200000) {
                            Service.chatNPC(p, (short) npcid, "Cần 20000000 lượng");
                            break;
                        } else {
                            int tl = (int)Util.nextInt(100);
                            if (tl < 90) {
                               Service.chatNPC(p, (short) npcid, "Tiến Hóa Thất Bại");
                                p.upluongMessage(-100000);
                                p.c.removeItemBags(773, 10);
                                break;
                            } else {
                                Manager.chatKTG(" Chúc Mừng người chơi " + p.c.name + " Vừa Tiến Hóa Dị Long Thiếu Niên Con Lên Dị Long Trưởng Thành! ");
                                Service.chatNPC(p, (short) npcid, "Chúc mừng bạn đã ấp được Dị Long Trưởng Thành");
                                final Item itemup = ItemTemplate.itemDefault(871);
                                
                                 itemup.upgrade = 16;
                                p.upluongMessage(-200000);
                                p.c.removeItemBags(773, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                }
            }
              break;
            case 4 :{
              Server.manager.sendTB(p, "Hướng dẫn", "- Trứng Có Thể kiếm được từ việc săn BOSS."
                      + "\n Trứng sẽ có tỷ lệ hỏng Cao."
                      + "\n Tỷ Lệ Trứng hỏng là 70 %."
                      + "\n Để Tiến Hóa Rồng cần 10 loại Rồng Cấp Thấp hơn."
                      + "\n Tỷ lệ nâng :"
                      + "\n Rồng Thiếu Niên : 20 %"
                      + "\n Rồng Trưởng Thành : 10 %");
                break;      
                      }
        }       
    }
    public static void npcott(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                
                case 0 : {
                if (p.c.level < 50) {
                            Service.chatNPC(p, (short) npcid, "Mày không đủ tuổi chơi với tao.");
                            return;
                        }

                        if (p.c.quantityItemyTotal(944) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 1 lon bia để cá cược.");
                            return;
                        }
                 else{
                     double percent = Util.nextInt(100);
                     if (percent >= 0 && percent < 33) {
                     p.conn.sendMessageLog( "Npc ra kéo. Bạn thua 1 lon bia");
                     
                     p.c.removeItemBags(944, 1);
                     } else if (percent >= 33 && percent < 66){
                     p.conn.sendMessageLog( "Npc ra bao. Hòa rồi");
                     } else if (percent >= 66 && percent < 99){
                     p.conn.sendMessageLog( "Npc ra búa. bạn thắng,nhận 500k");
                     p.c.addItemBag(false, ItemTemplate.itemDefault(945, false));
                     break;
                        }
                    }           
               
                break;
            }
                 case 1 : {
                if (p.c.level < 50) {
                            Service.chatNPC(p, (short) npcid, "Mày không đủ tuổi chơi với tao.");
                            return;
                        }

                        if (p.c.quantityItemyTotal(944) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 1 lon bia 333 để cá cược.");
                            return;
                        }
                 else{
                     double percent = Util.nextInt(100);
                     if (percent >= 0 && percent < 33) {
                     p.conn.sendMessageLog( "Npc ra bao. Bạn thua 1 lon bia 333");
                     
                     p.c.removeItemBags(820, 1);
                     } else if (percent >= 33 && percent < 66){
                     p.conn.sendMessageLog( "Npc ra búa. Hòa rồi");
                     } else if (percent >= 66 && percent < 99){
                     p.conn.sendMessageLog( "Npc ra kéo. bạn thắng,nhận 500k");
                     p.c.addItemBag(false, ItemTemplate.itemDefault(945, false));
                     break;
                          }
                    }           
               
                break;
            }
             case 2 : {
                if (p.c.level < 50) {
                            Service.chatNPC(p, (short) npcid, "Mày không đủ tuổi chơi với tao.");
                            return;
                        }

                        if (p.c.quantityItemyTotal(944) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 1 lon bia 333 để cá cược.");
                            return;
                        }
                 else{
                     double percent = Util.nextInt(100);
                     if (percent >= 0 && percent < 33) {
                     p.conn.sendMessageLog( "Npc ra búa. Bạn thua 1 đồng tiền");
                     
                     p.c.removeItemBags(820, 1);
                     } else if (percent >= 33 && percent < 66){
                     p.conn.sendMessageLog( "Npc ra kéo. Hòa rồi");
                     } else if (percent >= 66 && percent < 99){
                     p.conn.sendMessageLog( "Npc ra bao. bạn thắng,nhận 1 đồng xu vàng");
                     p.c.addItemBag(false, ItemTemplate.itemDefault(945, false));
                     break;
                    }
                }           
               
                break;
            }
        }
                break;
    } 
                case 1 : {
                
                
                       


                        if (p.c.quantityItemyTotal(945) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 5 triệu để đổi.");
                            return;
                        }else{
                        double percent = Util.nextInt(101);
                        if (percent < 50){
                        p.conn.sendMessageLog( "Hơi đen.Bạn nhận được linh chi ngàn năm");
                        p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));
                        p.c.removeItemBags(945, 10);
                        } else if (percent >= 50 && percent < 75){
                        p.conn.sendMessageLog( "Cũng được.Bạn nhận được linh chi Vạn năm");
                        p.c.removeItemBags(945, 10);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(540, false));
                        } else if (percent >= 75 && percent < 87){
                        p.conn.sendMessageLog( "Cũng được.Bạn nhận được bình rượu ủ 5 Vạn năm");
                        p.c.removeItemBags(945, 10);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(946, false));
                        }else if (percent >= 87 && percent < 95){
                        p.conn.sendMessageLog( "May mắn.Bạn nhận được bình rượu ủ 10 Vạn năm");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " Đã May mắn đổi được bình rượu ủ 10 vạn năm! " );
                        p.c.removeItemBags(945, 10);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(947, false));
                        }else if (percent >= 95 && percent < 99){
                        p.conn.sendMessageLog( "Nhân Phẩm Tốt.Bạn nhận được bình rượu ủ 20 Vạn năm");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " nhân phẩm tốt,Đã May mắn đổi được bình rượu ủ 20 vạn năm! " );
                        p.c.removeItemBags(945, 10);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(948, false));
                        }else if (percent == 100 ){
                        p.conn.sendMessageLog( "Nhân Phẩm Thượng Thừa.Bạn nhận được bình rượu ủ 50 Vạn năm");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " Nhân Phẩm Thượng thừa,Đã May mắn đổi được bình rượu ử 50 vạn năm! " );
                        p.c.removeItemBags(945, 10);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(949, false));
                         break;
                        }
                    } 
                 break;                  
                }
            
        
        case 2 :{
                Server.manager.sendTB(p, "Hướng dẫn", "-Oẳn tù tì cùng npc, bạn thắng nhận 500k,thua mất 1 lon bia 333 \n- Bao > Búa > Kéo > Bao \n- Mất 5tr 1 lượt đổi ngẫu nhiên \n Tỉ Lệ :\n Linh chi ngàn năm : 50%.\n Linh chi vạn năm : 22%.\n Linh chi 5 vạn năm : 8%.\n bình rượu ủ 10 vạn năm : 4%.\n bình rượu ủ 20 vạn năm : 3%.\n bình rượu ủ 50 vạn năm : 1%.");
                break;
            }
        }
    }
    public static void npcbaove(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                      case 0:{

                        if (p.c.quantityItemyTotal(959) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có danh thiếp cấp 1.");
                            return;
                            } else{
                        }
                            p.c.removeItemBags(959, 1);
                            Item itemup = ItemTemplate.itemDefault(954);
                            itemup.quantity = 50;                                   
                            p.c.addItemBag(false, itemup);
                            break;
                      }
                      }
                switch(b3) {
                      case 1:{

                        if (p.c.quantityItemyTotal(960) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có danh thiếp cấp 2.");
                            return;
                            } else{
                        }
                            p.c.removeItemBags(960, 1);
                            Item itemup = ItemTemplate.itemDefault(954);
                            itemup.quantity = 100;                                   
                            p.c.addItemBag(false, itemup);
                            break;
                      }
                      }
                switch(b3) {
                      case 2:{

                        if (p.c.quantityItemyTotal(961) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có danh thiếp cấp 3.");
                            return;
                            } else{
                        }
                            p.c.removeItemBags(961, 1);
                            Item itemup = ItemTemplate.itemDefault(954);
                            itemup.quantity = 200;                                   
                            p.c.addItemBag(false, itemup);
                            break;
                      }
                      }
                switch(b3) {
                      case 3:{

                        if (p.c.quantityItemyTotal(962) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có danh thiếp cấp 4.");
                            return;
                            } else{
                        }
                            p.c.removeItemBags(962, 1);
                            Item itemup = ItemTemplate.itemDefault(954);
                            itemup.quantity = 400;                                   
                            p.c.addItemBag(false, itemup);
                            break;
                      }
                      }
                switch(b3) {
                      case 4:{

                        if (p.c.quantityItemyTotal(963) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có danh thiếp cấp 5.");
                            return;
                            } else{
                        }
                            p.c.removeItemBags(963, 1);
                            Item itemup = ItemTemplate.itemDefault(954);
                            itemup.quantity = 600;                                   
                            p.c.addItemBag(false, itemup);
                            break;
                      }
                      }
                switch(b3) {
                      case 5:{

                        if (p.c.quantityItemyTotal(964) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có danh thiếp cấp 6.");
                            return;
                            } else{
                        }
                            p.c.removeItemBags(964, 1);
                            Item itemup = ItemTemplate.itemDefault(954);
                            itemup.quantity = 1200;                                   
                            p.c.addItemBag(false, itemup);
                            break;
                      }
                      }
                switch(b3) {
                    case 6 :{
                Server.manager.sendTB(p, "Hướng dẫn", "-danh thiếp cấp 1 đổi đc 50 hồn hoàn \n- danh thiếp cấp 2 đổi đc 100 hồn hoàn \n- danh thiếp cấp 3 đổi đc 200 hồn hoàn \n danh thiếp cấp 4 đổi đc 400 hồn hoàn\n danh thiếp cấp 5 đổi đc 600 hồn hoàn.\n danh thiếp cấp 6 đổi đc 1k hồn hoàn");
                break;  
                    }
        }
                break;  
                    }
     }
     }
    public static void npcnhanhoncot(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                    case 0:{
                        if (p.c.lvnhc !=1){
                    p.conn.sendMessageLog("hồn sư phải phải là hồn cốt 1 vạn năm");
                    return;
                        }
                         if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                         }
                    else if (p.luong < 50000) {
                    Service.chatNPC(p, (short) npcid, "Cần 50k lượng");
                    break;
               
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 15) {
                        p.c.luongTN =- 50000 ;
                        p.upluongMessage(-50000);
                        p.conn.sendMessageLog("hấp thụ hồn cốt 1 vạn năm thất bại ");
                        Server.manager.chatKTG("Hồn Sư " + p.c.name + " đã hấp thu thất bại hồn cốt 1 vạn năm ! " );
                         return;
                       } else{
                            p.c.luongTN =- 50000 ;
                            p.upluongMessage(-50000);
                            p.c.addItemBag(false, ItemTemplate.itemDefault(955));
                            p.c.lvnhc = 2;
                            p.conn.sendMessageLog("hấp thụ hồn cốt 1 vạn năm thành công ");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " đã hấp thu hồn cốt 1 vạn năm thành công ! " );
                    }
                    }
                            break;
                }
 
                case 1:{
                         if (p.c.lvnhc !=7){
                    p.conn.sendMessageLog("hồn sư phải phải là hồn cốt 7 vạn năm");
                    return;
                        }
                         if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                         }
                    else if (p.luong < 250000) {
                    Service.chatNPC(p, (short) npcid, "Cần 250k lượng");
                    break;
               
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 15) {
                        p.c.luongTN =- 250000 ;
                        p.upluongMessage(-250000);
                        p.conn.sendMessageLog("hấp thụ hồn cốt 7 vạn năm thất bại ");
                        Server.manager.chatKTG("Hồn Sư " + p.c.name + " đã hấp thu thất bại hồn cốt 7 vạn năm ! " );
                         return;
                       } else{
                            p.c.luongTN =- 250000 ;
                            p.upluongMessage(-250000);
                            p.c.addItemBag(false, ItemTemplate.itemDefault(956));
                            p.c.lvnhc = 8;
                            p.conn.sendMessageLog("hấp thụ hồn cốt 7 vạn năm thành công ");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " đã hấp thu hồn cốt 7 vạn năm thành công ! " );
                    }
                    }
                            break;
                }
 
                case 2:{
                         if (p.c.lvnhc !=10){
                    p.conn.sendMessageLog("hồn sư phải phải là hồn cốt 100 vạn năm");
                    return;
                        }
                         if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                return;
                         }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
               
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.conn.sendMessageLog("hấp thụ hồn cốt 100 vạn năm thất bại ");
                        Server.manager.chatKTG("Hồn Sư " + p.c.name + " đã hấp thu thất bại hồn cốt 100 vạn năm ! " );
                         return;
                    
                       } else{
                            p.c.luongTN =- 500000 ;
                            p.upluongMessage(-500000);
                            p.c.addItemBag(false, ItemTemplate.itemDefault(957));
                            p.c.lvnhc = 15;
                            p.conn.sendMessageLog("hấp thụ hồn cốt 100 vạn năm thành công ");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " đã hấp thu hồn cốt 100 vạn năm thành công ! " );
                          
                    }
                    }
                         break; 
                    }
   }
                          break; 
                    }
     }
                }
                
                
                
                
        
       public static void npctinhfox(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                
                case 0 : {
                if (p.c.level < 150) {
                            Service.chatNPC(p, (short) npcid, " Yêu cầu level trên 150 ,con nít đi chỗ khác chơi.");
                            return;
                        }

                        if (p.c.quantityItemyTotal(945) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 500k để cá cược.");
                            return;
                        }
                 else{
                     double percent = Util.nextInt(100);
                     if (percent >= 0 && percent < 33) {
                     p.conn.sendMessageLog( "đoán sai rồi lew lew");
                     
                     p.c.removeItemBags(945, 1);
                     } else if (percent >= 33 && percent < 66){
                     p.conn.sendMessageLog( "đoán sai rồi lew lew");
                     } else if (percent >= 66 && percent < 99){
                     p.conn.sendMessageLog( "đúng rồi bạn nhận đc vé giới thiệu");
                     p.c.addItemBag(false, ItemTemplate.itemDefault(965, false));
                     break;
                        }
                    }           
               
                break;
            }
                 case 1 : {
                if (p.c.level < 150) {
                            Service.chatNPC(p, (short) npcid, "Yêu cầu level trên 150 ,con nít đi chỗ khác chơi.");
                            return;
                        }

                        if (p.c.quantityItemyTotal(945) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 1 500k để cá cược.");
                            return;
                        }
                 else{
                     double percent = Util.nextInt(100);
                     if (percent >= 0 && percent < 33) {
                     p.conn.sendMessageLog( "đoán sai rồi lew lew");
                     
                     p.c.removeItemBags(945, 1);
                     } else if (percent >= 33 && percent < 66){
                     p.conn.sendMessageLog( "đoán sai rồi lew lew");
                     } else if (percent >= 66 && percent < 99){
                     p.conn.sendMessageLog( "đúng rồi bạn nhận đc 1 vé giới thiệu");
                     p.c.addItemBag(false, ItemTemplate.itemDefault(965, false));
                     break;
                          }
                    }           
               
                break;
            }
             case 2 : {
                if (p.c.level < 150) {
                            Service.chatNPC(p, (short) npcid, "Yêu cầu level trên 150 ,con nít đi chỗ khác chơi.");
                            return;
                        }

                        if (p.c.quantityItemyTotal(945) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 500k để cá cược.");
                            return;
                        }
                 else{
                     double percent = Util.nextInt(100);
                     if (percent >= 0 && percent < 33) {
                     p.conn.sendMessageLog( "đoán sai rồi lew lew");
                     
                     p.c.removeItemBags(945, 1);
                     } else if (percent >= 33 && percent < 66){
                     p.conn.sendMessageLog( "đoán sai rồi lew lew");
                     } else if (percent >= 66 && percent < 99){
                     p.conn.sendMessageLog( "đúng rồi bạn nhận đc vé giới thiệu");
                     p.c.addItemBag(false, ItemTemplate.itemDefault(965, false));
                     break;
                    }
                }           
               
                break;
            }
        }
                break;
    } 
                case 1 : {
                
                
                       


                        if (p.c.quantityItemyTotal(965) < 5) {
                            Service.chatNPC(p, (short) npcid, "Cần có ít nhất 5 vé giới thiệu.");
                            return;
                        }else{
                        double percent = Util.nextInt(101);
                        if (percent < 50){
                        p.conn.sendMessageLog( "Hơi đen.Bạn nhận được danh thiếp cấp 1 ");
                        p.c.addItemBag(false, ItemTemplate.itemDefault(959, false));
                        p.c.removeItemBags(965, 5);
                        } else if (percent >= 50 && percent < 75){
                        p.conn.sendMessageLog( "Cũng được.Bạn nhận được danh thiếp cấp 2");
                        p.c.removeItemBags(965, 5);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(960, false));
                        } else if (percent >= 75 && percent < 87){
                        p.conn.sendMessageLog( "Cũng được.Bạn nhận được danh thiếp cấp 3");
                        p.c.removeItemBags(965, 5);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(961, false));
                        }else if (percent >= 87 && percent < 95){
                        p.conn.sendMessageLog( "May mắn.Bạn nhận được danh thiếp cấp 4");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " Đã May mắn đổi được danh thiếp cấp 4! " );
                        p.c.removeItemBags(965, 5);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(962, false));
                        }else if (percent >= 95 && percent < 99){
                        p.conn.sendMessageLog( "Nhân Phẩm Tốt.Bạn nhận được danh thiếp cấp 5");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " nhân phẩm tốt,Đã May mắn đổi được danh thiếp cấp 5! " );
                        p.c.removeItemBags(965, 5);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(963, false));
                        }else if (percent == 100 ){
                        p.conn.sendMessageLog( "Nhân Phẩm Thượng Thừa.Bạn nhận được danh thiếp cấp 6");
                        Server.manager.chatKTG("Người chơi " + p.c.name + " Nhân Phẩm Thượng thừa,Đã May mắn đổi được danh thiếp cấp 6! " );
                        p.c.removeItemBags(965, 5);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(964, false));
                         break;
                        }
                    } 
                 break;                  
                }
            case 2 :{
                Server.manager.sendTB(p, "Hướng dẫn", "-đoán vé giới thiệu cùng Hoa Sơn , bạn đoán đúng nhận vé giới thiệu,k đúng mất 500k \n- 3 ô kiểu gì cũng có 1 cái có \n- Mất 5 vé giới thiệu để  1 lượt đổi ngẫu nhiên \n Tỉ Lệ :\n danh thiếp cấp 1 : 50%.\n danh thiếp cấp 2 : 22%.\n danh thiếp cấp 3 : 8%.\n danh thiếp cấp 4 : 4%.\n danh thiếp cấp 5 : 3%.\n danh thiếp cấp 6 : 1%.");
                break;
            }
        }
    }
  
    public static void npchoncot(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {         
            case 0: {
                switch (b3) {
                    case 0: {
                    
                    if (p.c.lvhc !=0){
                    p.conn.sendMessageLog("hồn sư đã mở hồn cốt");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 300){
                    p.conn.sendMessageLog("hồn sư không đủ 300 hồn hoàn");
                    return;
                }
                    else if (p.luong < 100000) {
                    Service.chatNPC(p, (short) npcid, "Cần 100k lượng");
                    break;
                } 
                    if (p.c.exphl < 5000000){
                    p.conn.sendMessageLog("Không đủ 5 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                         p.c.luongTN =- 100000 ;
                         p.upluongMessage(-100000);
                         p.c.removeItemBags(954, 50);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 100000 ;
                        p.upluongMessage(-100000);
                        p.c.exphl -= 5000000;
                        p.c.removeItemBags(954, 300);
                        p.c.lvhc = 1;
                        p.c.lvnhc = 1;
                        p.c.get().potential0 += 5000;
                        p.c.get().potential1 += 5000;
                        p.c.get().potential2 += 5000;
                        p.c.get().potential3 += 5000; 
                        
                 Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 1 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 1 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.lvhc != 1){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 1 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 400){
                    p.conn.sendMessageLog("hồn sư không đủ 400 hồn hoàn");
                    return;
                }
                     else if (p.luong < 120000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1200k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 10000000){
                    p.conn.sendMessageLog("Không đủ 10 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 1200000 ;
                         p.upluongMessage(-1200000);
                         p.c.removeItemBags(954, 100);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 120000 ;
                        p.upluongMessage(-120000);
                        p.c.exphl -= 10000000;
                        p.c.removeItemBags(954, 400);
                        p.c.lvhc = 2;
                        p.c.get().potential0 += 10000;
                        p.c.get().potential1 += 10000;
                        p.c.get().potential2 += 10000;
                        p.c.get().potential3 += 10000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 2 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 2 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.lvhc != 2){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 2 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 500){
                    p.conn.sendMessageLog("hồn sư không đủ 500 hồn hoàn");
                    return;
                }
                     else if (p.luong < 140000) {
                    Service.chatNPC(p, (short) npcid, "Cần 140k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 15000000){
                    p.conn.sendMessageLog("Không đủ 15 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 140000 ;
                         p.upluongMessage(-140000);
                         p.c.removeItemBags(954, 150);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 140000 ;
                        p.upluongMessage(-140000);
                        p.c.exphl -= 15000000;
                        p.c.removeItemBags(954, 500);
                        p.c.lvhc = 3;
                        p.c.get().potential0 += 15000;
                        p.c.get().potential1 += 15000;
                        p.c.get().potential2 += 15000;
                        p.c.get().potential3 += 15000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 3 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 3 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.lvhc != 3){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 3 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 600){
                    p.conn.sendMessageLog("hồn sư không đủ 600 hồn hoàn");
                    return;
                }
                     else if (p.luong < 160000) {
                    Service.chatNPC(p, (short) npcid, "Cần 160k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 15000000){
                    p.conn.sendMessageLog("Không đủ 15 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 160000 ;
                         p.upluongMessage(-160000);
                         p.c.removeItemBags(954, 200);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 160000 ;
                        p.upluongMessage(-160000);
                        p.c.exphl -= 15000000;
                        p.c.removeItemBags(954, 600);
                        p.c.lvhc = 4;
                        p.c.get().potential0 += 20000;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential2 += 20000;
                        p.c.get().potential3 += 20000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 4 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 4 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.lvhc != 4){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 4 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 700){
                    p.conn.sendMessageLog("hồn sư không đủ 700 hồn hoàn");
                    return;
                }
                     else if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Cần 200k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 20000000){
                    p.conn.sendMessageLog("Không đủ 20 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 200000 ;
                         p.upluongMessage(-200000);
                         p.c.removeItemBags(954, 200);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 200000 ;
                        p.upluongMessage(-200000);
                        p.c.exphl -= 20000000;
                        p.c.removeItemBags(954, 700);
                        p.c.lvhc = 5;
                        p.c.get().potential0 += 30000;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential2 += 30000;
                        p.c.get().potential3 += 30000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 5 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 5 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.lvhc != 5){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 5 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 800){
                    p.conn.sendMessageLog("hồn sư không đủ 800 hồn hoàn");
                    return;
                }
                     else if (p.luong < 250000) {
                    Service.chatNPC(p, (short) npcid, "Cần 250k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 20000000){
                    p.conn.sendMessageLog("Không đủ 20 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 250000 ;
                         p.upluongMessage(-250000);
                         p.c.removeItemBags(954, 200);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 250000 ;
                        p.upluongMessage(-250000);
                        p.c.exphl -= 20000000;
                        p.c.removeItemBags(954, 800);
                        p.c.lvhc = 6;
                        p.c.get().potential0 += 50000;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential2 += 50000;
                        p.c.get().potential3 += 50000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 6 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 6 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.lvhc != 6){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 6 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 900){
                    p.conn.sendMessageLog("hồn sư không đủ 900 hồn hoàn");
                    return;
                }
                     else if (p.luong < 300000) {
                    Service.chatNPC(p, (short) npcid, "Cần 300k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 25000000){
                    p.conn.sendMessageLog("Không đủ 25 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 300000 ;
                         p.upluongMessage(-300000);
                         p.c.removeItemBags(954, 300);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 300000 ;
                        p.upluongMessage(-300000);
                        p.c.exphl -= 25000000;
                        p.c.removeItemBags(954, 900);
                        p.c.lvhc = 7;
                        p.c.lvnhc = 7;
                        p.c.get().potential0 += 70000;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential2 += 70000;
                        p.c.get().potential3 += 70000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 7 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 7 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.lvhc != 7){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 7 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 1000){
                    p.conn.sendMessageLog("hồn sư không đủ 1000 hồn hoàn");
                    return;
                }
                     else if (p.luong < 350000) {
                    Service.chatNPC(p, (short) npcid, "Cần 350k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 30000000){
                    p.conn.sendMessageLog("Không đủ 30 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 25) {
                        p.c.luongTN =- 350000 ;
                         p.upluongMessage(-350000);
                         p.c.removeItemBags(954, 300);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 350000 ;
                        p.upluongMessage(-350000);
                        p.c.exphl -= 30000000;
                        p.c.removeItemBags(954, 1000);
                        p.c.lvhc = 8;
                        p.c.get().potential0 += 90000;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential2 += 90000;
                        p.c.get().potential3 += 90000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 8 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 8 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.lvhc != 8){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 8 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 1100){
                    p.conn.sendMessageLog("hồn sư không đủ 1100 hồn hoàn");
                    return;
                }
                     else if (p.luong < 400000) {
                    Service.chatNPC(p, (short) npcid, "Cần 400k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 30000000){
                    p.conn.sendMessageLog("Không đủ 30 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 20) {
                        p.c.luongTN =- 400000 ;
                         p.upluongMessage(-400000);
                         p.c.removeItemBags(954, 400);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 400000 ;
                        p.upluongMessage(-400000);
                        p.c.exphl -= 30000000;
                        p.c.removeItemBags(954, 1100);
                        p.c.lvhc = 9;
                        p.c.get().potential0 += 110000;
                        p.c.get().potential1 += 110000;
                        p.c.get().potential2 += 110000;
                        p.c.get().potential3 += 110000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 9 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 9 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
                    case 9: {
                    
                    if (p.c.lvhc != 9){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 9 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 1500){
                    p.conn.sendMessageLog("hồn sư không đủ 1500 hồn hoàn");
                    return;
                }
                     else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 35000000){
                    p.conn.sendMessageLog("Không đủ 35 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                        p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(954, 500);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.exphl -= 35000000;
                        p.c.removeItemBags(954, 1500);
                        p.c.lvhc = 10;
                        p.c.get().potential0 += 130000;
                        p.c.get().potential1 += 130000;
                        p.c.get().potential2 += 130000;
                        p.c.get().potential3 += 130000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 10 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 10 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
}
                    case 10: {
                    
                    if (p.c.lvhc != 10){
                    p.conn.sendMessageLog("hồn sư cần là hồn cốt 10 vạn năm");
                    return;
                    }
                    if(p.c.quantityItemyTotal(954) < 2000){
                    p.conn.sendMessageLog("hồn sư không đủ 2000 hồn hoàn");
                    return;
                }
                     else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.exphl < 35000000){
                    p.conn.sendMessageLog("Không đủ 35 triệu hồn lực để nâng hồn cốt, đi gặp npc tình fox để tu luyện hồn lực");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 5) {
                        p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(954, 800);
                         p.conn.sendMessageLog("non và xanh lắm làm lại đi");
                         Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt thất bại,ad said còn non và xanh lắm! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.exphl -= 35000000;
                        p.c.removeItemBags(954, 400);
                        p.c.lvhc = 11;
                        p.c.lvnhc = 10;
                        p.c.get().potential0 += 150000;
                        p.c.get().potential1 += 150000;
                        p.c.get().potential2 += 150000;
                        p.c.get().potential3 += 150000; 
                        
                Service.chatNPC(p, (short) npcid,"hồn sư đã tu luyện thành công hồn cốt 100 vạn năm");
                 Server.manager.chatKTG("Hồn Sư " + p.c.name + " tu luyện hồn cốt 100 vạn năm thành công,ad said đúng là con trai của ta! " );
                    }
                    }
                    break;
            }
          case 11: {
                    p.honcot();
                    break;
                }
                case 12: {
                    p.conn.sendMessageLog("hồn sư đăng có : " +p.c.exphl +" hồn lực");
                    break;
                }
                case 13: {
                    Server.manager.sendTB(p, "Điều Kiện tu luyện hồn cốt", "hồn sư phải tích đủ exp hồn lực bằng cách  đoán mò cùng NPC Hoa Sơn hoặc đánh boss"
                            + "\n>hồn cốt<"
                            + "\n-1 vạn năm cần 5 triệu exp hồn lực và 100k lượng và 300 hồn hoàn"
                            + "\n-2 vạn năm cần 10 triệu exp hồn lực và 120k lượng và 400 hồn hoàn"
                            + "\n-3 vạn năm cần 15 triệu exp hồn lực và 140k lượng và 500 hồn hoàn"
                            + "\n-4 vạn năm cần 15 triệu exp hồn lực và 160k lượng và 600 hồn hoàn"
                            + "\n-5 vạn năm cần 20 triệu exp hồn lực và 180k lượng và 700 hồn hoàn"
                            + "\n-6 vạn năm cần 20 triệu exp hồn lực và 200k lượng và 800 hồn hoàn"
                            + "\n-7 vạn năm cần 25 triệu exp hồn lực và 250k lượng và 900 hồn hoàn"
                            + "\n-8 vạn năm cần 25 triệu exp hồn lực và 300k lượng và 1000 hồn hoàn"
                            + "\n-9 vạn năm cần 30 triệu exp hồn lực và 350k lượng và 1100 hồn hoàn"
                            + "\n-10 vạn năm cần 35 triệu exp hồn lực và 500k lượng và 1500 hồn hoàn"
                            + "\n-100 vạn năm cần 35 triệu exp hồn lực và 1000k lượng và 2000 hồn hoàn"
                            + "\n-đột phá thành công sẽ cấp bậc và nhận đc hiệu ứng tương ứng"
                            + "\n-thất bại sẽ mất lượng exp giữ nguyên"
                            + "\n-mỗi cấp bậc sẽ nhận cộng dồn điểm tiềm năng  "
                            + "\n-BẢNG cấp bậc "
                            + "\n-1 vạn năm :5k + 2 vạn năm :10k  + 3 vạn năm :15k  + 4 vạn năm :20k \n"
                            + "5 vạn năm :30k + 6 vạn năm :50k  + 7 vạn năm :70k  + 8 vạn năm :90k  + 9 vạn năm :110k + 10 vạn năm :130k + 100 vạn năm :150k "
                            );
                    break;
                }
            }
               break;
        }    
        }
    }
    public static void npcvkthan(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                      case 0:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(993) < 250) {
                            Service.chatNPC(p, (short) npcid, "Cần có 250 mảnh dưa hấu.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(993, 100);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(993,250);
                        Item itemup = ItemTemplate.itemDefault(992);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công cánh thiên thần! " );
                      }
                      }
                        break;
            }
                      case 1:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(993) < 250) {
                            Service.chatNPC(p, (short) npcid, "Cần có 250 mảnh dưa hấu.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(993, 10);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(993, 250);
                        Item itemup = ItemTemplate.itemDefault(925);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công cánh địa ngục! " );
                      }
                      }
                        break;
            }
                      case 2:{

                       
                         if (p.c.quantityItemyTotal(993) < 150) {
                            Service.chatNPC(p, (short) npcid, "Cần có 150 mảnh dưa hấu.");
                            return;
                         }
                    else if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Cần 200k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 90) {
                         p.c.luongTN =- 200000 ;
                         p.upluongMessage(-200000);
                         
                         p.c.removeItemBags(993, 100);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 200000 ;
                        p.upluongMessage(-200000);
                        
                        p.c.removeItemBags(993, 150);
                        Item itemup = ItemTemplate.itemDefault(1003);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " Đá ngũ sắc! " );
                      }
                      }
                        break;
            }
                      case 3:{

                       
                         if (p.c.quantityItemyTotal(457) < 1) {
                            Service.chatNPC(p, (short) npcid, "Cần có 1 tử tinh thạch cao.");
                            return;
                         }
                    else if (p.luong < 10000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 90) {
                         p.c.luongTN =- 10000 ;
                         p.upluongMessage(-10000);
                         
                         p.c.removeItemBags(457, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000 ;
                        p.upluongMessage(-10000);
                        
                        p.c.removeItemBags(457, 1);
                        Item itemup = ItemTemplate.itemDefault(1003);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " Đá ngũ sắc! " );
                      }
                      }
                        break;
            }
                      case 4:{

                       
                         if (p.c.quantityItemyTotal(1003) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 đá ngũ sắc.");
                            return;
                         }
                    else if (p.luong < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10 tr lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 100) {
                         p.c.luongTN =- 10000000 ;
                         p.upluongMessage(-10000000);
                         
                         p.c.removeItemBags(1003, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000000 ;
                        p.upluongMessage(-10000000);
                        
                        p.c.removeItemBags(1003, 200);
                        Item itemup = ItemTemplate.itemDefault(1006);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " vk kiếm thần bia 333! " );
                      }
                      }
                        break;
            }
                      case 5:{

                       
                         if (p.c.quantityItemyTotal(1003) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 đá ngũ sắc.");
                            return;
                         }
                    else if (p.luong < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10 tr lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 100) {
                         p.c.luongTN =- 10000000 ;
                         p.upluongMessage(-10000000);
                         
                         p.c.removeItemBags(1003, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000000 ;
                        p.upluongMessage(-10000000);
                        
                        p.c.removeItemBags(1003, 200);
                        Item itemup = ItemTemplate.itemDefault(1007);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " vk tiêu thần bia 333! " );
                      }
                      }
                        break;
            }
                      case 6:{

                       
                         if (p.c.quantityItemyTotal(1003) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 đá ngũ sắc.");
                            return;
                         }
                    else if (p.luong < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10 tr lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 100) {
                         p.c.luongTN =- 10000000 ;
                         p.upluongMessage(-10000000);
                         
                         p.c.removeItemBags(1003, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000000 ;
                        p.upluongMessage(-10000000);
                        
                        p.c.removeItemBags(1003, 200);
                        Item itemup = ItemTemplate.itemDefault(1008);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " vk kunai thần bia 333! " );
                      }
                      }
                        break;
            }
                      case 7:{

                       
                         if (p.c.quantityItemyTotal(1003) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 đá ngũ sắc.");
                            return;
                         }
                    else if (p.luong < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10 tr lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 100) {
                         p.c.luongTN =- 10000000 ;
                         p.upluongMessage(-10000000);
                         
                         p.c.removeItemBags(1003, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000000 ;
                        p.upluongMessage(-10000000);
                        
                        p.c.removeItemBags(1003, 200);
                        Item itemup = ItemTemplate.itemDefault(1009);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " vk cung thần bia 333! " );
                      }
                      }
                        break;
            }
                      case 8:{

                       
                         if (p.c.quantityItemyTotal(1003) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 đá ngũ sắc.");
                            return;
                         }
                    else if (p.luong < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10 tr lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 100) {
                         p.c.luongTN =- 10000000 ;
                         p.upluongMessage(-10000000);
                         
                         p.c.removeItemBags(1003, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000000 ;
                        p.upluongMessage(-10000000);
                        
                        p.c.removeItemBags(1003, 200);
                        Item itemup = ItemTemplate.itemDefault(1010);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " vk đao thần bia 333! " );
                      }
                      }
                        break;
            }
                      case 9:{

                       
                         if (p.c.quantityItemyTotal(1003) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 đá ngũ sắc.");
                            return;
                         }
                    else if (p.luong < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10 tr lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 100) {
                         p.c.luongTN =- 10000000 ;
                         p.upluongMessage(-10000000);
                         
                         p.c.removeItemBags(1003, 1);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 10000000 ;
                        p.upluongMessage(-10000000);
                        
                        p.c.removeItemBags(1003, 200);
                        Item itemup = ItemTemplate.itemDefault(1011);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " vk quạt thần bia 333! " );
                      }
                      }
                        break;
            }
                      case 10 :{
                Server.manager.sendTB(p, "Điều Kiện ĐỔI ĐỒ", "người chơi cần đi đánh quái ở map huy chương hoặc map vip để lấy chuyển tinh thạch và mảnh dưa hấu "
                            + "\n>SỨ GIẢ<"
                            + "\n-đổi cánh thiên thần cần có 50 viên chuyển tinh thạch và 50 mảnh dưa hấu để đổi với tỉ lệ 30 %"
                            + "\n-đổi cánh địa ngục cần 50 viên chuyển tinh thạch và 50 mảnh dưa hấu tỉ lệ 30%"
                            + "\n-đổi đá ngũ sắc cần 150 mảnh dưa hấu tỉ lệ 90%"
                            + "\n-đổi đá ngũ sắc cần 1 tử tinh thạch cao tỉ lệ 90%" 
                            + "\n-đổi vk thần cần 200 đá ngũ sắc tỉ lệ 100%"
                            + "\n-mảnh dưa hấu là 1 vật phẩm cực hiếm nên ae phải biết tận dụng"
                            + "\n-nếu đổi thành công bạn sẽ đc đồ tương ứng"
                            + "\n-nếu xịt thì còn CÁI NỊT NHÉ"
                            + "\n-CHÚC CÁC BẠN THÀNH CÔNG" 
                        );
                        break;  
                    }
                }
            }
        }
    }
     public static void npctaphoa(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {
            case 0: {
                switch(b3) {
                      case 0:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(988);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công mặt nạ zoro! " );
                      }
                      }
                        break;
            }
                      case 1:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(989);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công áo zoro! " );
                      }
                      }
                        break;
            }
                      case 2:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(990);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công quần zoro! " );
                      }
                      }
                        break;
            }
                      case 3:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(991);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công chân thân zoro! " );
                      }
                      }
                        break;
            }
                      case 4:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(983);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công mặt nạ TNK! " );
                      }
                      }
                        break;
            }
                      case 5:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(984);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công áo TNK! " );
                      }
                      }
                        break;
            }
                      case 6:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(985);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                        Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công quần zoro! " );
                      }
                      }
                        break;
            }
                      case 7:{

                        if (p.c.quantityItemyTotal(454) < 50) {
                            Service.chatNPC(p, (short) npcid, "Cần có 50 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 10000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(454, 50);
                         p.c.removeItemBags(987, 10000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(986);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                            Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công chân thân zoro! " );
                      }
                      }
                        break;
            }
                       case 8:{

                        if (p.c.quantityItemyTotal(454) < 20) {
                            Service.chatNPC(p, (short) npcid, "Cần có 20 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 3000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 3000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Cần 200k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 200000 ;
                         p.upluongMessage(-200000);
                         p.c.removeItemBags(454, 20);
                         p.c.removeItemBags(987, 3000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(309);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                            Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công bánh băng hỏa! " );
                      }
                      }
                        break;
            }
                      case 9:{

                        if (p.c.quantityItemyTotal(454) < 20) {
                            Service.chatNPC(p, (short) npcid, "Cần có 20 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 3000) {
                            Service.chatNPC(p, (short) npcid, "Cần có 3000 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 200000) {
                    Service.chatNPC(p, (short) npcid, "Cần 200k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 200000 ;
                         p.upluongMessage(-200000);
                         p.c.removeItemBags(454, 20);
                         p.c.removeItemBags(987, 3000);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.removeItemBags(454, 50);
                        p.c.removeItemBags(987, 10000);
                        Item itemup = ItemTemplate.itemDefault(308);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                            Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công bánh phong lôi! " );
                      }
                      }
                        break;
            }
                      case 10:{

                        if (p.c.quantityItemyTotal(454) < 20) {
                            Service.chatNPC(p, (short) npcid, "Cần có 20 viên chuyển tinh thạch.");
                            return;
                           
                        }
                         if (p.c.quantityItemyTotal(987) < 300) {
                            Service.chatNPC(p, (short) npcid, "Cần có 300 viên đạn 7mm.");
                            return;
                         }
                    else if (p.luong < 100000) {
                    Service.chatNPC(p, (short) npcid, "Cần 100k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 100000 ;
                         p.upluongMessage(-100000);
                         p.c.removeItemBags(454, 20);
                         p.c.removeItemBags(987, 300);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.luongTN =- 100000 ;
                        p.upluongMessage(-100000);
                        p.c.removeItemBags(454, 20);
                        p.c.removeItemBags(987, 300);
                        Item itemup = ItemTemplate.itemDefault(976);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                            Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công huy chương câu cá! " );
                      }
                      }
                        break;
            }
                      case 11:{

                        
                         if (p.c.quantityItemyTotal(598) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 350 huyết long ngư.");
                            return;
                         }
                    else if (p.c.yen < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10tr yên");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.yenTN =- 10000000 ;
                         p.c.upyenMessage(-10000000);
                        
                         p.c.removeItemBags(598, 200);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.yenTN =- 10000000 ;
                        p.c.upyenMessage(-10000000);
                       
                        p.c.removeItemBags(598, 200);
                        Item itemup = ItemTemplate.itemDefault(994);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                            Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công túi 100k xu! " );
                      }
                      }
                        break;
            }
                       case 12:{

                        
                         if (p.c.quantityItemyTotal(598) < 200) {
                            Service.chatNPC(p, (short) npcid, "Cần có 200 huyết long ngư.");
                            return;
                         }
                    else if (p.c.yen < 10000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 10tr yên");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.yenTN =- 10000000 ;
                         p.c.upyenMessage(-10000000);
                        
                         p.c.removeItemBags(598, 200);
                         p.conn.sendMessageLog(" tiền hết tình tan đời tàn đổi đồ còn xịt đen lắm");
                         return;
                    }else{
                        p.c.yenTN =- 10000000 ;
                        p.c.upyenMessage(-10000000);
                       
                        p.c.removeItemBags(598, 200);
                        Item itemup = ItemTemplate.itemDefault(995);
                        itemup.quantity = 1;                                   
                        p.c.addItemBag(false, itemup);
                            Server.manager.chatKTG("người chơi " + p.c.name + " đổi thành công túi 100k lg! " );
                      }
                      }
                        break;
            }
                       case 15 :{
                Server.manager.sendTB(p, "Điều Kiện ĐỔI ĐỒ", "người chơi cần đi đánh quái lấy chuyển tinh thạch và đạn 7mm để đổi"
                            + "\n>TẠP HÓA<"
                            + "\n-đổi đồ cần có 50 viên chuyển tinh thạch và 10k đạn 7mm để đổi với tỉ lệ 30 %"
                            + "\n-đổi bánh trung thu phong lôi , băng hỏa cần 20 viên chuyển tinh thạch và 3k đạn tỉ lệ 30%"
                            + "\n-đổi huy chương câu cá cần  20 viên chuyển tinh thạch và 300 đạn 7 mm tỉ lệ 30%"
                            + "\n-đổi túi 100k xu  cần 200 huyết long ngư tỉ lệ 30%"
                            + "\n-đổi túi 100k lượng  cần 200 huyết long ngư tỉ lệ 30%"
                            + "\n-huyết long ngư có thể kiếm trong map huy chương câu cá, nếu may mắn có thể nhặt được mảnh dưa hấu dùng để đổi vk thiên thần , địa ngục"
                            + "\n-nếu đổi thành công bạn sẽ đc đồ tương ứng"
                            + "\n-nếu xịt thì còn CÁI NỊT NHÉ"
                            + "\n-CHÚC CÁC BẠN THÀNH CÔNG" 
                        );
                        break;  
                    }
                }
            }
        }
    }
    
    public static void npcphuhao(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {         
            case 0: {
                switch (b3) {
                    case 0: {
                    
                    if (p.c.lvph !=0){
                    p.conn.sendMessageLog("người chơi chưa mở phú hào ");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 25){
                    p.conn.sendMessageLog("cần có đủ 25 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 25);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 25);
                        p.c.lvph = 1;
                        p.c.get().potential1 += 5000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào thành công may mắn quá đi! " );
                    }
                    }
                    
                    break;
            }
                case 1: {
                    
                    if (p.c.lvph !=1){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào ");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 35){
                    p.conn.sendMessageLog("cần có đủ 35 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 35);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 35);
                        p.c.lvph = 2;
                        p.c.get().potential1 += 15000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 1 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 1 thành công may mắn quá đi! " );
                    }
                    }
                    
                    break;
            }
                case 2: {
                    
                    if (p.c.lvph !=2){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 1 ");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 45){
                    p.conn.sendMessageLog("cần có đủ 45 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 45);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 45);
                        p.c.lvph = 3;
                        p.c.get().potential1 += 25000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 2 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 2 thành công may mắn quá đi! " );
                    }
                    
                }
                    break;
            }
                case 3: {
                    
                    if (p.c.lvph !=3){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 55){
                    p.conn.sendMessageLog("cần có đủ 55 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 55);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 55);
                        p.c.lvph = 4;
                        p.c.get().potential1 += 35000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 3 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 3 thành công may mắn quá đi! " );
                    }
                    }
                    
                    break;
            }
                case 4: {
                    
                    if (p.c.lvph !=4){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 65){
                    p.conn.sendMessageLog("cần có đủ 65 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 65);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 65);
                        p.c.lvph = 5;
                        p.c.get().potential1 += 45000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 4 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 4 thành công may mắn quá đi! " );
                    }
                    
                    }
                    break;
            }
                case 5: {
                    
                    if (p.c.lvph !=5){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) <75){
                    p.conn.sendMessageLog("cần có đủ 75 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 75);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 75);
                        p.c.lvph = 6;
                        p.c.get().potential1 += 55000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 5 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 5 thành công may mắn quá đi! " );
                    }
                    
                    }
                    break;
            }
                case 6: {
                    
                    if (p.c.lvph !=6){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 85){
                    p.conn.sendMessageLog("cần có đủ 85 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 55);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 85);
                        p.c.lvph = 7;
                        p.c.get().potential1 += 65000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 6 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 6 thành công may mắn quá đi! " );
                    }
                    
                    }
                    break;
            }
                case 7: {
                    
                    if (p.c.lvph !=7){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 95){
                    p.conn.sendMessageLog("cần có đủ 95 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 95);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 95);
                        p.c.lvph = 8;
                        p.c.get().potential1 += 75000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 7 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 7 thành công may mắn quá đi! " );
                    }
                    
                    }
                    break;
            }
                case 8: {
                    
                    if (p.c.lvph !=8){
                    p.conn.sendMessageLog("người chơi chưa là  phú hào 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(454) < 105){
                    p.conn.sendMessageLog("cần có đủ 105 viên chuyển tinh thạch");
                    return;
                }
                    else if (p.luong < 500000) {
                    Service.chatNPC(p, (short) npcid, "Cần 500k lượng");
                    break;
                
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 500000 ;
                         p.upluongMessage(-500000);
                         p.c.removeItemBags(454, 105);
                         p.conn.sendMessageLog(" cần cù bù nhiều tiền cố lên bạn ê");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã nâng phú hào thất bại đen lắm làm lại đi bạn ê! " );
                         return;
                    }else{
                        p.c.luongTN =- 500000 ;
                        p.upluongMessage(-500000);
                        p.c.removeItemBags(454, 55);
                        p.c.lvph = 9;
                        p.c.get().potential1 += 85000; 
                 Service.chatNPC(p, (short) npcid,"bạn đã mở phú hào 8 thành công");
                 Server.manager.chatKTG("người chơi " + p.c.name + " đã mở phú hào 8 thành công may mắn quá đi! " );
                    }
                    
                    }
                    break;
            }
                 case 9: {
                    p.phuhao();
                    break;
                }
              
                 case 10: {
                    Server.manager.sendTB(p, "Điều Kiện học phú hào", "người chơi cần đi đánh quái lấy chuyển tinh thạch để học"
                            + "\n>PHÚ HÀO<"
                            + "\n-phú hào gồm 9 cấp"
                            + "\n-người chơi cần kiếm đủ lượng và chuyển tinh thạch để nâng mỗi cấp"
                            + "\n-nâng thành công mỗi cấp sẽ nhận  tiềm năng thân pháp và tỉ lệ đánh quái sẽ ra thêm lượng"
                            + "\n-đánh quái ra lương sẽ bao gồm all map"
                            + "\n-CHÚC CÁC BẠN THÀNH CÔNG"
                            
                            );
                }
                
                    break;
                }
                }
            }
   }
        public static void npcnhucthan(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {         
            case 0: {
                switch (b3) {
                    case 0: {
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=0){
                    p.conn.sendMessageLog("bạn chưa luyện nhục thân");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 300){
                    p.conn.sendMessageLog("bạn cần có đủ 300 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1500){
                    p.conn.sendMessageLog("Không đủ 1500 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1500;
                        p.c.removeItemBags(842, 300);
                        p.c.lvnt = 1;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential0 += 20000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 1");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 1! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=1){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 1");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 350){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1700){
                    p.conn.sendMessageLog("Không đủ 1700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 350);
                        p.c.lvnt = 2;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential0 += 30000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 2");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 2! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=2){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 400){
                    p.conn.sendMessageLog("bạn cần có đủ 400 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1900){
                    p.conn.sendMessageLog("Không đủ 1900 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1900;
                        p.c.removeItemBags(842, 400);
                        p.c.lvnt = 3;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential0 += 40000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 3");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 3! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=3){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 450){
                    p.conn.sendMessageLog("bạn cần có đủ 450 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2000){
                    p.conn.sendMessageLog("Không đủ 2000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 450);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 450);
                        p.c.lvnt = 4;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential0 += 50000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 4");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 4! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=4){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 500){
                    p.conn.sendMessageLog("bạn cần có đủ 500 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2300){
                    p.conn.sendMessageLog("Không đủ 2300 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 500);
                        p.c.lvnt = 5;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential0 += 60000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 5");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 5! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=5){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 600){
                    p.conn.sendMessageLog("bạn cần có đủ 600 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2700){
                    p.conn.sendMessageLog("Không đủ 2700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 2700;
                        p.c.removeItemBags(842, 600);
                        p.c.lvnt = 6;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential0 += 70000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 6");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 6! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=6){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 700){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3200){
                    p.conn.sendMessageLog("Không đủ 3200 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3200;
                        p.c.removeItemBags(842, 700);
                        p.c.lvnt = 7;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential0 += 80000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 7");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 7! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=7){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 800){
                    p.conn.sendMessageLog("bạn cần có đủ 800 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3700){
                    p.conn.sendMessageLog("Không đủ 3700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3700;
                        p.c.removeItemBags(842, 800);
                        p.c.lvnt = 8;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential0 += 90000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 8");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 8! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.nclass != 1){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kiếm !");
                            return;
                        }
                    if (p.c.lvnt !=8){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 8");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 900){
                    p.conn.sendMessageLog("bạn cần có đủ 900 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 5000){
                    p.conn.sendMessageLog("Không đủ 5000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 5000;
                        p.c.removeItemBags(842, 900);
                        p.c.lvnt = 9;
                        p.role = 10;
                        p.c.get().potential1 += 100000;
                        p.c.get().potential0 += 100000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 9");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 9 nhận được danh hiệu đệ nhất kiếm! " );
                    }
                    }
                    break;
            }
                }
            }
        
               case 1: {
                switch (b3) {
                    case 0: {
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=0){
                    p.conn.sendMessageLog("bạn chưa luyện nhục thân");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 300){
                    p.conn.sendMessageLog("bạn cần có đủ 300 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1500){
                    p.conn.sendMessageLog("Không đủ 1500 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1500;
                        p.c.removeItemBags(842, 300);
                        p.c.lvnt = 1;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential0 += 20000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 1");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 1! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=1){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 1");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 350){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1700){
                    p.conn.sendMessageLog("Không đủ 1700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 350);
                        p.c.lvnt = 2;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential0 += 30000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 2");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 2! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=2){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 400){
                    p.conn.sendMessageLog("bạn cần có đủ 400 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1900){
                    p.conn.sendMessageLog("Không đủ 1900 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1900;
                        p.c.removeItemBags(842, 400);
                        p.c.lvnt = 3;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential0 += 40000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 3");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 3! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái nunai !");
                            return;
                        }
                    if (p.c.lvnt !=3){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 450){
                    p.conn.sendMessageLog("bạn cần có đủ 450 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2000){
                    p.conn.sendMessageLog("Không đủ 2000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 450);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 450);
                        p.c.lvnt = 4;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential0 += 50000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 4");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 4! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=4){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 500){
                    p.conn.sendMessageLog("bạn cần có đủ 500 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2300){
                    p.conn.sendMessageLog("Không đủ 2300 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 500);
                        p.c.lvnt = 5;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential0 += 60000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 5");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 5! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=5){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 600){
                    p.conn.sendMessageLog("bạn cần có đủ 600 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2700){
                    p.conn.sendMessageLog("Không đủ 2700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 2700;
                        p.c.removeItemBags(842, 600);
                        p.c.lvnt = 6;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential0 += 70000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 6");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 6! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=6){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 700){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3200){
                    p.conn.sendMessageLog("Không đủ 3200 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3200;
                        p.c.removeItemBags(842, 700);
                        p.c.lvnt = 7;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential0 += 80000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 7");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 7! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=7){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 800){
                    p.conn.sendMessageLog("bạn cần có đủ 800 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3700){
                    p.conn.sendMessageLog("Không đủ 3700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3700;
                        p.c.removeItemBags(842, 800);
                        p.c.lvnt = 8;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential0 += 90000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 8");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 8! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.nclass != 3){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái kunai !");
                            return;
                        }
                    if (p.c.lvnt !=8){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 8");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 900){
                    p.conn.sendMessageLog("bạn cần có đủ 900 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 5000){
                    p.conn.sendMessageLog("Không đủ 5000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 5000;
                        p.c.removeItemBags(842, 900);
                        p.c.lvnt = 9;
                        p.role = 11;
                        p.c.get().potential1 += 100000;
                        p.c.get().potential0 += 100000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 9");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 9 nhận được danh hiệu đệ nhất kunai! " );
                    }
                    }
                    break;
            }
                }
               }
               case 2: {
                switch (b3) {
                    case 0: {
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=0){
                    p.conn.sendMessageLog("bạn chưa luyện nhục thân");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 300){
                    p.conn.sendMessageLog("bạn cần có đủ 300 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1500){
                    p.conn.sendMessageLog("Không đủ 1500 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1500;
                        p.c.removeItemBags(842, 300);
                        p.c.lvnt = 1;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential0 += 20000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 1");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 1! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=1){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 1");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 350){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1700){
                    p.conn.sendMessageLog("Không đủ 1700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 350);
                        p.c.lvnt = 2;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential0 += 30000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 2");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 2! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=2){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 400){
                    p.conn.sendMessageLog("bạn cần có đủ 400 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1900){
                    p.conn.sendMessageLog("Không đủ 1900 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1900;
                        p.c.removeItemBags(842, 400);
                        p.c.lvnt = 3;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential0 += 40000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 3");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 3! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=3){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 450){
                    p.conn.sendMessageLog("bạn cần có đủ 450 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2000){
                    p.conn.sendMessageLog("Không đủ 2000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 450);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 450);
                        p.c.lvnt = 4;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential0 += 50000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 4");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 4! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=4){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 500){
                    p.conn.sendMessageLog("bạn cần có đủ 500 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2300){
                    p.conn.sendMessageLog("Không đủ 2300 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 500);
                        p.c.lvnt = 5;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential0 += 60000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 5");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 5! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=5){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 600){
                    p.conn.sendMessageLog("bạn cần có đủ 600 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2700){
                    p.conn.sendMessageLog("Không đủ 2700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 2700;
                        p.c.removeItemBags(842, 600);
                        p.c.lvnt = 6;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential0 += 70000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 6");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 6! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=6){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 700){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3200){
                    p.conn.sendMessageLog("Không đủ 3200 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3200;
                        p.c.removeItemBags(842, 700);
                        p.c.lvnt = 7;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential0 += 80000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 7");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 7! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=7){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 800){
                    p.conn.sendMessageLog("bạn cần có đủ 800 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3700){
                    p.conn.sendMessageLog("Không đủ 3700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3700;
                        p.c.removeItemBags(842, 800);
                        p.c.lvnt = 8;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential0 += 90000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 8");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 8! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.nclass != 5){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái đao !");
                            return;
                        }
                    if (p.c.lvnt !=8){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 8");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 900){
                    p.conn.sendMessageLog("bạn cần có đủ 900 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 5000){
                    p.conn.sendMessageLog("Không đủ 5000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 5000;
                        p.c.removeItemBags(842, 900);
                        p.c.lvnt = 9;
                        p.role = 12;
                        p.c.get().potential1 += 100000;
                        p.c.get().potential0 += 100000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 9");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 9 nhận được danh hiệu đệ nhất đao! " );
                    }
                    }
                    break;
            }
                }
               }
               
          case 3: {
                switch (b3) {
          case 0: {
                    p.nhucthan();
                   return;
                }
                }
          }
          case 4: {
                switch (b3) {
                case 0: {
                    p.conn.sendMessageLog("bạn đăng có : " +p.c.expnt +" kinh nghiệm nhục thân");
                    return;
                }
                }
          }
          
          case 5: {
                switch (b3) {
                case 0: {
                    Server.manager.sendTB(p, "Điều Kiện tu luyện nhục thân", "bạn phải tích đủ exp nhục thân và viên đan dược  bằng cách làm sự kiện tại npc tiên nữ"
                            + "\n>Nhục Thân<"
                            + "\n-nhục thân cấp 1 cần 300 viên đan dược và 1k5 kinh nghiệm nhục thân và 1000k lượng "
                            + "\n-nhục thân cấp 2 cần 350 viên đan dược và 1k7 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 3 cần 400 viên đan dược và 1k9 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 4 cần 450 viên đan dược và 2000 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 5 cần 500 viên đan dược và 2k3 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 6 cần 600 viên đan dược và 2k7 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 7 cần 700 viên đan dược và 3k kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 8 cần 800 viên đan dược và 3k5 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 9 cần 900 viên đan dược và 5k kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nâng nhục thân lên cấp 9 ae sẽ được nhận thêm danh hiệu tương ứng với phái ae đăng học"
                            + "\n-chúc ae thành công"
                            + "\n-đột phá thành công sẽ cấp bậc và nhận đc hiệu ứng tương ứng"
                            + "\n-thất bại sẽ mất lượng exp giữ nguyên"
                            
                            );
                    break;
                }
            }
               break;
        }    
        }
  }
  
 public static void npcTaiXiu(Player p, byte npcid, byte menuId, byte b3) throws InterruptedException {
    switch (menuId) {
      case 0:
        Server.manager.taixiu[0].InfoTaiXiu(p);
        break;
      case 1:
        Service.sendInputDialog(p, (short)222, "Nhập tiền cược(chia hết cho 10):");
        break;
      case 2:
        Service.sendInputDialog(p, (short)223, "Nhập tiền cược(chia hết cho 10):");
        break;
      case 3:
        try {
          String a = "";
          int i2 = 1;
          for (SoiCau check : SoiCau.soicau) {
            a = a + i2 + ". " + check.time + " - " + check.ketqua + " - " + check.soramdom + ".\n";
            i2++;
          } 
          Server.manager.sendTB(p, "Soi Cầu", a);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        break;
      case 4:
          Server.manager.sendTB(p, "Hướng dẫn","Số lượng đặt cược phải là số chia hết cho 10.\n"
                  + "Khi đã đặt cược không được thoát game, nếu thoát game sẽ bị mất số tiền cược và admin sẽ không giải quyết.\n"
                  + "Mỗi phiên cược sẽ là 1 phút, khi thời gian còn 10s sẽ không thể đặt cược.\n"
                  + "Khi đã đặt tài thì không thể đặt xỉu và ngược lại.\n"
                  + "Có thể đặt nhiều lần trong 1 phiên\n"
                  + "Không được đặt trên 50tr lượng, xu, yên đặt mất không chịu!");
        break;
    } 
  }

        public static void npcnhucthan1(Player p, byte npcid, byte menuId, byte b3) {
        switch(menuId) {         
            case 0: {
                switch (b3) {
                    case 0: {
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=0){
                    p.conn.sendMessageLog("bạn chưa luyện nhục thân");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 300){
                    p.conn.sendMessageLog("bạn cần có đủ 300 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1500){
                    p.conn.sendMessageLog("Không đủ 1500 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1500;
                        p.c.removeItemBags(842, 300);
                        p.c.lvnt = 1;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential3 += 20000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 1");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 1! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=1){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 1");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 350){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1700){
                    p.conn.sendMessageLog("Không đủ 1700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 350);
                        p.c.lvnt = 2;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential3 += 30000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 2");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 2! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=2){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 400){
                    p.conn.sendMessageLog("bạn cần có đủ 400 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1900){
                    p.conn.sendMessageLog("Không đủ 1900 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1900;
                        p.c.removeItemBags(842, 400);
                        p.c.lvnt = 3;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential3 += 40000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 3");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 3! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=3){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 450){
                    p.conn.sendMessageLog("bạn cần có đủ 450 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2000){
                    p.conn.sendMessageLog("Không đủ 2000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 450);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 450);
                        p.c.lvnt = 4;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential3 += 50000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 4");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 4! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=4){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 500){
                    p.conn.sendMessageLog("bạn cần có đủ 500 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2300){
                    p.conn.sendMessageLog("Không đủ 2300 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 500);
                        p.c.lvnt = 5;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential3 += 60000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 5");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 5! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=5){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 600){
                    p.conn.sendMessageLog("bạn cần có đủ 600 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2700){
                    p.conn.sendMessageLog("Không đủ 2700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 2700;
                        p.c.removeItemBags(842, 600);
                        p.c.lvnt = 6;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential3 += 70000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 6");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 6! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=6){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 700){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3200){
                    p.conn.sendMessageLog("Không đủ 3200 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3200;
                        p.c.removeItemBags(842, 700);
                        p.c.lvnt = 7;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential3 += 80000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 7");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 7! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=7){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 800){
                    p.conn.sendMessageLog("bạn cần có đủ 800 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3700){
                    p.conn.sendMessageLog("Không đủ 3700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3700;
                        p.c.removeItemBags(842, 800);
                        p.c.lvnt = 8;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential3 += 90000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 8");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 8! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.nclass != 2){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái tiêu !");
                            return;
                        }
                    if (p.c.lvnt !=8){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 8");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 900){
                    p.conn.sendMessageLog("bạn cần có đủ 900 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 5000){
                    p.conn.sendMessageLog("Không đủ 5000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 5000;
                        p.c.removeItemBags(842, 900);
                        p.c.lvnt = 9;
                        p.role = 13;
                        p.c.get().potential1 += 100000;
                        p.c.get().potential3 += 100000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 9");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 9 nhận được danh hiệu đệ nhất tiêu! " );
                    }
                    }
                    break;
            }
                }
            }
        
               case 1: {
                switch (b3) {
                    case 0: {
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=0){
                    p.conn.sendMessageLog("bạn chưa luyện nhục thân");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 300){
                    p.conn.sendMessageLog("bạn cần có đủ 300 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1500){
                    p.conn.sendMessageLog("Không đủ 1500 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1500;
                        p.c.removeItemBags(842, 300);
                        p.c.lvnt = 1;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential3 += 20000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 1");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 1! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=1){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 1");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 350){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1700){
                    p.conn.sendMessageLog("Không đủ 1700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 350);
                        p.c.lvnt = 2;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential3 += 30000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 2");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 2! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=2){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 400){
                    p.conn.sendMessageLog("bạn cần có đủ 400 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1900){
                    p.conn.sendMessageLog("Không đủ 1900 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1900;
                        p.c.removeItemBags(842, 400);
                        p.c.lvnt = 3;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential3 += 40000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 3");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 3! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=3){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 450){
                    p.conn.sendMessageLog("bạn cần có đủ 450 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2000){
                    p.conn.sendMessageLog("Không đủ 2000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 450);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 450);
                        p.c.lvnt = 4;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential3 += 50000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 4");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 4! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=4){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 500){
                    p.conn.sendMessageLog("bạn cần có đủ 500 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2300){
                    p.conn.sendMessageLog("Không đủ 2300 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 500);
                        p.c.lvnt = 5;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential0 += 60000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 5");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 5! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=5){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 600){
                    p.conn.sendMessageLog("bạn cần có đủ 600 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2700){
                    p.conn.sendMessageLog("Không đủ 2700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 2700;
                        p.c.removeItemBags(842, 600);
                        p.c.lvnt = 6;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential3 += 70000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 6");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 6! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=6){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 700){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3200){
                    p.conn.sendMessageLog("Không đủ 3200 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3200;
                        p.c.removeItemBags(842, 700);
                        p.c.lvnt = 7;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential3 += 80000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 7");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 7! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=7){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 800){
                    p.conn.sendMessageLog("bạn cần có đủ 800 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3700){
                    p.conn.sendMessageLog("Không đủ 3700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3700;
                        p.c.removeItemBags(842, 800);
                        p.c.lvnt = 8;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential3 += 90000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 8");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 8! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.nclass != 4){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái cung !");
                            return;
                        }
                    if (p.c.lvnt !=8){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 8");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 900){
                    p.conn.sendMessageLog("bạn cần có đủ 900 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 5000){
                    p.conn.sendMessageLog("Không đủ 5000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 5000;
                        p.c.removeItemBags(842, 900);
                        p.c.lvnt = 9;
                        p.role = 14;
                        p.c.get().potential1 += 100000;
                        p.c.get().potential3 += 100000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 9");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 9 nhận được danh hiệu đệ nhất cung! " );
                    }
                    }
                    break;
            }
                }
               }
               case 2: {
                switch (b3) {
                    case 0: {
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=0){
                    p.conn.sendMessageLog("bạn chưa luyện nhục thân");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 300){
                    p.conn.sendMessageLog("bạn cần có đủ 300 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1500){
                    p.conn.sendMessageLog("Không đủ 1500 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1500;
                        p.c.removeItemBags(842, 300);
                        p.c.lvnt = 1;
                        p.c.get().potential1 += 20000;
                        p.c.get().potential3 += 20000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 1");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 1! " );
                    }
                    }
                    break;
            }
                    case 1: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=1){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 1");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 350){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1700){
                    p.conn.sendMessageLog("Không đủ 1700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 350);
                        p.c.lvnt = 2;
                        p.c.get().potential1 += 30000;
                        p.c.get().potential3 += 30000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 2");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 2! " );
                    }
                    }
                    break;
            }
                    case 2: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=2){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 2");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 400){
                    p.conn.sendMessageLog("bạn cần có đủ 400 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 1900){
                    p.conn.sendMessageLog("Không đủ 1900 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1900;
                        p.c.removeItemBags(842, 400);
                        p.c.lvnt = 3;
                        p.c.get().potential1 += 40000;
                        p.c.get().potential0 += 40000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 3");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 3! " );
                    }
                    }
                    break;
            }
                    case 3: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=3){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 3");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 450){
                    p.conn.sendMessageLog("bạn cần có đủ 450 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2000){
                    p.conn.sendMessageLog("Không đủ 2000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 450);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 450);
                        p.c.lvnt = 4;
                        p.c.get().potential1 += 50000;
                        p.c.get().potential0 += 50000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 4");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 4! " );
                    }
                    }
                    break;
            }
                    case 4: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=4){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 4");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 500){
                    p.conn.sendMessageLog("bạn cần có đủ 500 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2300){
                    p.conn.sendMessageLog("Không đủ 2300 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 1700;
                        p.c.removeItemBags(842, 500);
                        p.c.lvnt = 5;
                        p.c.get().potential1 += 60000;
                        p.c.get().potential3 += 60000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 5");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 5! " );
                    }
                    }
                    break;
            }
                    case 5: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=5){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 5");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 600){
                    p.conn.sendMessageLog("bạn cần có đủ 600 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 2700){
                    p.conn.sendMessageLog("Không đủ 2700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 2700;
                        p.c.removeItemBags(842, 600);
                        p.c.lvnt = 6;
                        p.c.get().potential1 += 70000;
                        p.c.get().potential0 += 70000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 6");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 6! " );
                    }
                    }
                    break;
            }
                    case 6: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=6){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 6");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 700){
                    p.conn.sendMessageLog("bạn cần có đủ 350 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3200){
                    p.conn.sendMessageLog("Không đủ 3200 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3200;
                        p.c.removeItemBags(842, 700);
                        p.c.lvnt = 7;
                        p.c.get().potential1 += 80000;
                        p.c.get().potential3 += 80000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 7");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 7! " );
                    }
                    }
                    break;
            }
                    case 7: {
                    
                    if (p.c.nclass != 6){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=7){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 7");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 800){
                    p.conn.sendMessageLog("bạn cần có đủ 800 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 3700){
                    p.conn.sendMessageLog("Không đủ 3700 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 3700;
                        p.c.removeItemBags(842, 800);
                        p.c.lvnt = 8;
                        p.c.get().potential1 += 90000;
                        p.c.get().potential3 += 90000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 8");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 8! " );
                    }
                    }
                    break;
            }
                    case 8: {
                    
                    if (p.c.nclass != 6  ){
                            Service.chatNPC(p, (short)npcid, "bạn phải là phái quạt !");
                            return;
                        }
                    if (p.c.lvnt !=8){
                    p.conn.sendMessageLog("bạn chưa đạt nhục thân cấp 8");
                    return;
                    }
                    if(p.c.quantityItemyTotal(842) < 900){
                    p.conn.sendMessageLog("bạn cần có đủ 900 viên đạn dược");
                    return;
                }
                    else if (p.luong < 1000000) {
                    Service.chatNPC(p, (short) npcid, "Cần 1000k lượng");
                    break;
                }
                    
                    if (p.c.expnt < 5000){
                    p.conn.sendMessageLog("Không đủ 5000 kinh nghiệm để nâng nhục thân, hãy đi làm sk đi rồi kiếm exp rồi quay lại đây");
                    return;
                    } else{
                    byte tinhfox = (byte) Util.nextInt(1, 100);
                    if (tinhfox > 10) {
                         p.c.luongTN =- 1000000 ;
                         p.upluongMessage(-1000000);
                         p.c.removeItemBags(842, 300);
                         p.conn.sendMessageLog("dễ vậy thì ad đã k thèm npc này");
                         Server.manager.chatKTG("người chơi " + p.c.name + " đã tu luyện thất bại dễ lắm lại mà nâng! " );
                         return;
                    }else{
                        p.c.luongTN =- 1000000 ;
                        p.upluongMessage(-1000000);
                        p.c.expnt -= 5000;
                        p.c.removeItemBags(842, 900);
                        p.c.lvnt = 9;
                        p.role = 15;
                        p.c.get().potential1 += 100000;
                        p.c.get().potential3 += 100000;
                 Service.chatNPC(p, (short) npcid,"hên hết phần thiên hạ bạn đã nâng nhục thân lên cấp 9");
                 Server.manager.chatKTG("người chơi " + p.c.name + " hên hết phần thiện hạ đã nâng được nhục thân lên cấp 9 nhận được danh hiệu đệ nhất đao! " );
                    }
                    }
                    break;
            }
                }
               }
               
          case 3: {
                switch (b3) {
          case 0: {
                    p.nhucthan();
                   return;
                }
                }
          }
          case 4: {
                switch (b3) {
                case 0: {
                    p.conn.sendMessageLog("bạn đăng có : " +p.c.expnt +" kinh nghiệm nhục thân");
                    return;
                }
                }
          }
          
          case 5: {
                switch (b3) {
                case 0: {
                    Server.manager.sendTB(p, "Điều Kiện tu luyện nhục thân", "bạn phải tích đủ exp nhục thân và viên đan dược  bằng cách làm sự kiện tại npc tiên nữ"
                            + "\n>Nhục Thân<"
                            + "\n-nhục thân cấp 1 cần 300 viên đan dược và 1k5 kinh nghiệm nhục thân và 1000k lượng "
                            + "\n-nhục thân cấp 2 cần 350 viên đan dược và 1k7 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 3 cần 400 viên đan dược và 1k9 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 4 cần 450 viên đan dược và 2000 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 5 cần 500 viên đan dược và 2k3 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 6 cần 600 viên đan dược và 2k7 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 7 cần 700 viên đan dược và 3k kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 8 cần 800 viên đan dược và 3k5 kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nhục thân cấp 9 cần 900 viên đan dược và 5k kinh nghiệm nhục thân và 1000k lượng"
                            + "\n-nâng nhục thân lên cấp 9 ae sẽ được nhận thêm danh hiệu tương ứng với phái ae đăng học"
                            + "\n-chúc ae thành công"
                            + "\n-đột phá thành công sẽ cấp bậc và nhận đc hiệu ứng tương ứng"
                            + "\n-thất bại sẽ mất lượng exp giữ nguyên"
                            
                            );
                    break;
                }
            }
               break;
        }    
        }
  }
  public static void npcSanBoss(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:{
                switch (b3) {
                
                    case 0:{
                if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    if(p.c.potential3 < 10000000){
                        p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 10tr tiềm năng sức mạnh!");
                       return;}}
                    if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    if(p.c.potential0 < 10000000){
                     p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 10tr tiềm năng sức mạnh!");
                     return;}}
                       Map ma = Manager.getMapid(171);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
                    
                    }      
                  case 1 :{ 
                            if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    if(p.c.potential3 < 13000000){
                        p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 13tr tiềm năng sức mạnh!");
                       return;}}
                    if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    if(p.c.potential0 < 13000000){
                     p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 13tr tiềm năng sức mạnh!");
                     return;}}
                       Map ma = Manager.getMapid(172);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;}
                  case 2 : {
                            if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    if(p.c.potential3 < 15000000){
                        p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 15tr tiềm năng sức mạnh!");
                       return;}}
                    if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    if(p.c.potential0 < 15000000){
                     p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 15tr tiềm năng sức mạnh!");
                     return;}}
                       Map ma = Manager.getMapid(173);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;}
                  case 3 : {
                            if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    if(p.c.potential3 < 17000000){
                        p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 17tr tiềm năng sức mạnh!");
                       return;}}
                    if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    if(p.c.potential0 < 17000000){
                     p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 17tr tiềm năng sức mạnh!");
                     return;}}
                       Map ma = Manager.getMapid(174);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
                  } 
                  case 4 : {
                            if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        if (p.c.nclass == 2 || p.c.nclass == 4 || p.c.nclass == 6){
                    if(p.c.potential3 < 19000000){
                        p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 19tr tiềm năng sức mạnh!");
                       return;}}
                    if (p.c.nclass == 1 || p.c.nclass == 3 || p.c.nclass == 5){
                    if(p.c.potential0 < 19000000){
                     p.sendAddchatYellow("Không thể vào Map Boss, cần đạt hơn 19tr tiềm năng sức mạnh!");
                     return;}}
                       Map ma = Manager.getMapid(175);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
                  }
                  
                }
            }
            case 1: {
                 if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(176);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
                }
            case 2: {
                 if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(177);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
                }
            case 3: {
                 if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(178);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
                }
            case 4: {
                    Server.manager.sendTB(p, "Hướng dẫn ", "Xin chào bạn đã đến với sever Tuổi Thơ"
                            + "\n                            Săn Boss"
                            + "\n-Boss Lão Đại săn đồ +16 có chỉ số từ 1 đến 149% đồ 1x-4x !"
                            + "\n-Boss Lão Tam săn đồ +16 có chỉ số từ 1 đến 249% đồ 5x-7x, trừ xu và lượng,mp,hp!"
                            + "\n-Boss Lão Nhị săn đồ +17(Tinh luyện 9) có chỉ số từ 1 đến 349% đồ 8x-9x, trừ xu và lượng,mp,hp !"
                            + "\n-Boss Đại Địa +17(Tinh luyện 10) có chỉ số từ 1 đến 449% đồ all hệ, trừ xu và lượng,mp,hp,tiềm năng sức mạnh!"
                            + "\n-Đồ Boss Sơn Tinh và Thủy Tinh sẽ săn được thú cưỡi chỉ số khủng, trừ xu và lượng,mp,hp,tiềm năng sức mạnh!"
                            + "\n                            Săn Lượng"
                            + "\n-Vào 21h00 đến 21h15p hằng ngày sẽ săn ra lượng!"
                            + "\n-Yêu cầu cần từ 4 người trong 1 khu để đánh ra lượng(không có không được)!"
                            + "\n                            Đại Chiến PK"
                            + "\n-Yêu cầu 1 Map phải có trên 21 người PK (Cứu Sát), chỉ 7h tối đến 9h tối thôi nhé!"
                            +"\n-PK ngẫu nhiên sẽ ra các vật phẩm ngẫu nhiên như chuyển tinh thạch,thẻ bài gia tộc,túi nâng hành trang, các vật phẩm nâng sức mạnh cho bản thân!"
                            + "\n                           Săn Boss Thế Giới!"
                            + "\n-Đánh Boss chết sẽ may mắn nhận được vô vàn đồ đạc, hiệu ứng ngon!"
                            + "\n-Nhớ là chỉ 19h00  đến 19h30 thôi nhé!"
                            );
                     break;
                }
    }
    }
  public static void npcDiHoc(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:{
           if(p.c.vip < 10 ){
           p.conn.sendMessageLog("Yêu Cầu Víp 10!");
           return;
           }     
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(180);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;       
                   }
            case 1:{
           if(p.c.vip < 10 ){
           p.conn.sendMessageLog("Yêu Cầu Víp 10!");
           return;
           }     
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(181);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;       
                   }
            case 2:{
           if(p.c.vip < 10 ){
           p.conn.sendMessageLog("Yêu Cầu Víp 10!");
           return;
           }     
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(182);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;       
                   }
            case 3:{
           if(p.c.vip < 17 ){
           p.conn.sendMessageLog("Yêu Cầu Víp 18!");
           return;
           } 
           if(p.c.dihoc <= 11 ){
           p.conn.sendMessageLog("Yêu Cầu Cấp Đi Học 12!");
           return;
           } 
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(183);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;       
                   }
            case 4:{
                 Server.manager.sendTB(p, "Hướng dẫn ", "Xin chào bạn đã đến với sever Tuổi Thơ"
                            + "\n                     Đi Học Cấp 1"
                            + "\n-Khi ấn vào npc Thầy Tuổi Thơ sẽ có học cấp 1, Bạn sẽ được đưa đến 1 map riêng để đánh quái lấy exp đi học !"
                            + "\n-Khi nâng lên cấp độ từ cấp 1 đến cấp 3 sẽ có hiệu ứng riêng! "
                            + "\n-Khi hoàn thành cấp 3 thành công sẽ nhận được một số đồ đạc sịn hơn nước chấm, Săn Boss sẽ không mất gì( có tỉ lệ ra đồ ) ! " 
                            );
            
            }
        }}
  public static void npcCayHoaDen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch(menuId) {
            case 0:{
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(184);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;             
                   }
            case 1:{
            if(p.c.quantityItemyTotal(1031) < 1000){
            p.conn.sendMessageLog("Cần 1k mảnh đồ để đổi đồ!");
            return;
            }
            if (p.c.getBagNull() == 0) {
                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                        return;
                    }
           int tl1 = (int) Util.nextInt(1, 10);
            if(tl1 == 5){
       short[] arId = new short[]{94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,134,135,136,137,154,155,208,207,206,205,
           204,203,202,201,200,199,198,197,196,195,194,127,128,129,138,139,144,145,158,159,130,131,132,133,140,141,142,143,148,149,150,258,259,151,152,153,160,161,162,163,164,165,166,167,168,169,170,
           171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,192,193};
                      short idI = arId[Util.nextInt(arId.length)];
                  Item itemup = ItemTemplate.itemDefault(idI);
                     itemup.isLock = false;
                    itemup = ItemTemplate.itemDefault(idI);
                    short cs2 = (short) Util.nextInt(1,16);
                     itemup.upgrade = 16;
                     itemup.options.clear();
                     short cs = (short) Util.nextInt(1000,3000);
                     short cs1 = (short) Util.nextInt(100,250);
                      short cs3 = (short) Util.nextInt(1,5);
                      short cs4 = (short) Util.nextInt(70,80);

                        itemup.options.add(new Option(73, cs*cs2));
                        itemup.options.add(new Option(6, cs*cs2));
                        itemup.options.add(new Option(7,cs*cs2));
                        itemup.options.add(new Option(0, cs*cs2));
                        itemup.options.add(new Option(1, cs*cs2));
                        itemup.options.add(new Option(8, cs4));
                        itemup.options.add(new Option(9, cs4));
                        itemup.options.add(new Option(10, cs*cs2));
                        itemup.options.add(new Option(14, cs4));
                        itemup.options.add(new Option(18, cs*cs2));
                        itemup.options.add(new Option(27, cs));
                        itemup.options.add(new Option(30, cs));
                        itemup.options.add(new Option(33, cs*cs2));
                        itemup.options.add(new Option(34, cs*cs2));
                        itemup.options.add(new Option(35, cs*cs2));
                        itemup.options.add(new Option(37, cs4));
                        itemup.options.add(new Option(38, cs*cs2));
                        itemup.options.add(new Option(54, cs4));
                       // itemup.options.add(new Option(55, cs4));
                        itemup.options.add(new Option(56, cs4));
                        itemup.options.add(new Option(57, cs));
                        itemup.options.add(new Option(58, cs4));
                        itemup.options.add(new Option(60, cs4));
                        itemup.options.add(new Option(61, cs4));
                        itemup.options.add(new Option(67, cs4));                        
                        itemup.options.add(new Option(100, cs4));
                        itemup.options.add(new Option(101, cs4));
                        itemup.options.add(new Option(102, cs1));
                        itemup.options.add(new Option(103, cs1));
                        p.c.addItemBag(true, itemup);  
                        p.c.removeItemBags(1031, 1000);
                    }else{
                    p.conn.sendMessageLog("Đổi thất bại!");
                        p.c.removeItemBags(1031, 1000);
                            return; }}
            break;
            }
        }
  
  public static void npcCayDen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
      Calendar calendar = Calendar.getInstance();
                byte hours = (byte) calendar.get(Calendar.HOUR_OF_DAY); 
                 byte phut = (byte) calendar.get(Calendar.MINUTE);
        switch(menuId) {
           case 0:{
               if(hours < 19 || hours > 22 ){ 
                   p.conn.sendMessageLog("Từ 19h00 đến 22h59 thì mới được vào!");
                          return;
               }
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(185);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;       
        }
           case 1:{
            if(p.c.quantityItemyTotal(1041) < 100){
            p.conn.sendMessageLog("Cần 100 cây kem để đổi đồ!");
            return;
            }
            if (p.c.getBagNull() == 0) {
                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                        return;
                    }
           int tl1 = (int) Util.nextInt(1, 10);
            if(tl1 == 5){
       short[] arId = new short[]{439,440,441,442};
                      short idI = arId[Util.nextInt(arId.length)];
                  Item itemup = ItemTemplate.itemDefault(idI);
                     itemup.isLock = false;
                    itemup = ItemTemplate.itemDefault(idI);
                    short cs2 = (short) Util.nextInt(1,16);
                     itemup.upgrade = 16;
                     itemup.options.clear();
                     short cs = (short) Util.nextInt(1000,3000);
                     short cs1 = (short) Util.nextInt(100,250);
                      short cs3 = (short) Util.nextInt(1,5);
                      short cs4 = (short) Util.nextInt(20,50);

                        itemup.options.add(new Option(73, cs*cs2));
                        itemup.options.add(new Option(6, cs*cs2));
                        itemup.options.add(new Option(7,cs*cs2));
                        itemup.options.add(new Option(0, cs*cs2));
                        itemup.options.add(new Option(1, cs*cs2));
                        itemup.options.add(new Option(8, cs4));
                        itemup.options.add(new Option(9, cs4));
                        itemup.options.add(new Option(10, cs*cs2));
                        itemup.options.add(new Option(14, cs4));
                        itemup.options.add(new Option(18, cs*cs2));
                        itemup.options.add(new Option(27, cs));
                        itemup.options.add(new Option(30, cs));
                        itemup.options.add(new Option(33, cs*cs2));
                        itemup.options.add(new Option(34, cs*cs2));
                        itemup.options.add(new Option(35, cs*cs2));
                        itemup.options.add(new Option(37, cs4));
                        itemup.options.add(new Option(38, cs*cs2));
                        itemup.options.add(new Option(54, cs4));
                       // itemup.options.add(new Option(55, cs4));
                        itemup.options.add(new Option(56, cs4));
                        itemup.options.add(new Option(57, cs));
                        itemup.options.add(new Option(58, cs4));
                        itemup.options.add(new Option(60, cs4));
                        itemup.options.add(new Option(61, cs4));
                        itemup.options.add(new Option(67, cs4));                        
                        itemup.options.add(new Option(100, cs4));
                        itemup.options.add(new Option(101, cs4));
                        itemup.options.add(new Option(102, cs1));
                        itemup.options.add(new Option(103, cs1));
                        p.c.addItemBag(true, itemup);  
                        p.c.removeItemBags(1031, 100);
                    }else{
                    p.conn.sendMessageLog("Đổi thất bại!");
                        p.c.removeItemBags(1031, 100);
                            return; }}
            case 2:{
            Server.manager.sendTB(p, "Hướng dẫn ", "Xin chào bạn đã đến với sever Tuổi Thơ"
                            + "\n                     Săn Trang Bị Thú Cưỡi!"
                            + "\n-Khi vào Map 20h-21h hằng ngày sẽ đánh ra trang bị xe thú cưỡi! "
                            + "\n-Yêu cầu trên 4 người, từ 19h00 đến 22h59 sẽ đánh ra! "
                            );
            }
        }

  }
   public static void npcBossCui(Player p, byte npcid, byte menuId, byte b3) throws IOException {
      Calendar calendar = Calendar.getInstance();
                byte hours = (byte) calendar.get(Calendar.HOUR_OF_DAY); 
                 byte phut = (byte) calendar.get(Calendar.MINUTE);
        switch(menuId) {
           case 0:{
               if(hours < 6 || hours > 12 ){ 
                   p.conn.sendMessageLog("Từ 6h00 đến 12h59 thì mới được vào!");
                          return;
               }
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(186);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
        } 
           case 1:{
              if(hours < 13 || hours > 18 ){ 
                   p.conn.sendMessageLog("Từ 13h00 đến 18h59 thì mới được vào!");
                          return;
               }
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(187);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
        } 
           case 2:{
               if(hours < 19 || hours > 23 ){ 
                   p.conn.sendMessageLog("Từ 19h00 đến 23h59 thì mới được vào!");
                          return;
               }
           if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                    Map ma = Manager.getMapid(188);
                        TileMap area;
                        int var8;
                        for (var8 = 0; var8 < ma.area.length; ++var8) {
                            area = ma.area[var8];
                            if (area.numplayers < ma.template.maxplayers) {
                                p.c.tileMap.leave(p);
                                area.EnterMap1(p.c);
                                return;
                            }
                        }
                        p.endLoad(true);
                        break;
        } 
        }

  }
}