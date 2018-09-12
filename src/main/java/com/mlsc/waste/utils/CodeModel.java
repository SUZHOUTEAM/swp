package com.mlsc.waste.utils;

import java.io.Serializable;

public class CodeModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String label;

    public CodeModel(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 危废特性:WASTE_FEATURES
     */
    public enum WASTE_FEATURES {
        /**
         * T:毒性
         */
        T(new CodeModel("T", "毒性")),
        /**
         * C:腐蚀性
         */
        C(new CodeModel("C", "腐蚀性")),
        /**
         * I:易燃性
         */
        I(new CodeModel("I", "易燃性")),
        /**
         * R:反应性
         */
        R(new CodeModel("R", "反应性")),
        /**
         * In:感染性
         */
        In(new CodeModel("In", "感染性"));
        private CodeModel codeModel;

        WASTE_FEATURES(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (T.getCode().equals(code)) {
                labelString = T.getLabel();
            } else if (C.getCode().equals(code)) {
                labelString = C.getLabel();
            } else if (I.getCode().equals(code)) {
                labelString = I.getLabel();
            } else if (R.getCode().equals(code)) {
                labelString = R.getLabel();
            } else if (In.getCode().equals(code)) {
                labelString = In.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 危险废物种类:WASTE_FLAG
     */
    public enum WASTE_FLAG {
        /**
         * Explosive:爆炸性
         */
        Explosive(new CodeModel("Explosive", "爆炸性")),
        /**
         * Toxic:有毒
         */
        Toxic(new CodeModel("Toxic", "有毒")),
        /**
         * Flammable:易燃
         */
        Flammable(new CodeModel("Flammable", "易燃")),
        /**
         * Harmful:有害
         */
        Harmful(new CodeModel("Harmful", "有害")),
        /**
         * Oxidizing:助燃
         */
        Oxidizing(new CodeModel("Oxidizing", "助燃")),
        /**
         * Corrosive:腐蚀性
         */
        Corrosive(new CodeModel("Corrosive", "腐蚀性")),
        /**
         * Irritant:刺激性
         */
        Irritant(new CodeModel("Irritant", "刺激性")),
        /**
         * Asbestos:石棉
         */
        Asbestos(new CodeModel("Asbestos", "石棉"));

        private CodeModel codeModel;

        WASTE_FLAG(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (Explosive.getCode().equals(code)) {
                labelString = Explosive.getLabel();
            } else if (Toxic.getCode().equals(code)) {
                labelString = Toxic.getLabel();
            } else if (Flammable.getCode().equals(code)) {
                labelString = Flammable.getLabel();
            } else if (Harmful.getCode().equals(code)) {
                labelString = Harmful.getLabel();
            } else if (Oxidizing.getCode().equals(code)) {
                labelString = Oxidizing.getLabel();
            } else if (Corrosive.getCode().equals(code)) {
                labelString = Corrosive.getLabel();
            } else if (Irritant.getCode().equals(code)) {
                labelString = Irritant.getLabel();
            } else if (Asbestos.getCode().equals(code)) {
                labelString = Asbestos.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 废物形态:WASTE_PATTERN
     */
    public enum WASTE_PATTERN {
        /**
         * S:固态
         */
        S(new CodeModel("S", "固态")),
        /**
         * SS:半固体
         */
        SS(new CodeModel("SS", "半固体")),
        /**
         * L:液态
         */
        L(new CodeModel("L", "液态")),
        /**
         * G:气态
         */
        G(new CodeModel("G", "气态"));

        private CodeModel codeModel;

        WASTE_PATTERN(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (S.getCode().equals(code)) {
                labelString = S.getLabel();
            } else if (SS.getCode().equals(code)) {
                labelString = SS.getLabel();
            } else if (L.getCode().equals(code)) {
                labelString = L.getLabel();
            } else if (G.getCode().equals(code)) {
                labelString = G.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 计量单位:UNIT_TYPE
     */
    public enum UNIT_TYPE {
        /**
         * T:固态
         */
        T(new CodeModel("T", "吨")),

        KG(new CodeModel("KG", "千克")),

        G(new CodeModel("G", "克")),
        /**
         * C:只
         */
        C(new CodeModel("C", "只"));

        private CodeModel codeModel;

        UNIT_TYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (T.getCode().equals(code)) {
                labelString = T.getLabel();
            } else if (C.getCode().equals(code)) {
                labelString = C.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 危废产生源:WASTE_SOURCE
     */
    public enum WASTE_SOURCE {
        /**
         * G1:生产工艺过程产生
         */
        G1(new CodeModel("G1", "生产工艺过程产生")),
        /**
         * G2:事故（如泄漏）产生。包括溢出的污染物及清洁被污染设备过程中产生的废物等
         */
        G2(new CodeModel("G2", "事故（如泄漏）产生。包括溢出的污染物及清洁被污染设备过程中产生的废物等")),
        /**
         * G3:设备检修、清库等过程产生
         */
        G3(new CodeModel("G3", "设备检修、清库等过程产生")),
        /**
         * G4:其他
         */
        G4(new CodeModel("G4", "其他"));

        private CodeModel codeModel;

        WASTE_SOURCE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (G1.getCode().equals(code)) {
                labelString = G1.getLabel();
            } else if (G2.getCode().equals(code)) {
                labelString = G2.getLabel();
            } else if (G3.getCode().equals(code)) {
                labelString = G3.getLabel();
            } else if (G4.getCode().equals(code)) {
                labelString = G4.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 许可证危废许可方式:LIC_CALC_TYPE
     */
    public enum LIC_CALC_TYPE {
        /**
         * CALC_ALL:包含所有八位码
         */
        CALC_ALL(new CodeModel("CALC_ALL", "包含所有八位码")),
        /**
         * CALC_INCLUDE:包含指定八位
         */
        CALC_INCLUDE(new CodeModel("CALC_INCLUDE", "包含指定八位码")),
        /**
         * CALC_EXCEPT:不包含指定八位码
         */
        CALC_EXCEPT(new CodeModel("CALC_EXCEPT", "不包含指定八位码"));

        private CodeModel codeModel;

        LIC_CALC_TYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (CALC_ALL.getCode().equals(code)) {
                labelString = CALC_ALL.getLabel();
            } else if (CALC_INCLUDE.getCode().equals(code)) {
                labelString = CALC_INCLUDE.getLabel();
            } else if (CALC_EXCEPT.getCode().equals(code)) {
                labelString = CALC_EXCEPT.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 许可证审核状态:LIC_AUDIT
     */
    public enum LIC_AUDIT {
        /**
         * CREATE:企业创建
         */
        CREATE(new CodeModel("CREATE", "企业创建")),
        /**
         * CALC_INCLUDE:待审核
         */
        SUBMIT(new CodeModel("SUBMIT", "待审核")),
        /**
         * PASS:审核通过
         */
        PASS(new CodeModel("PASS", "审核通过")),
        /**
         * REFUSED:审核退回
         */
        REFUSED(new CodeModel("REFUSED", "审核退回"));

        private CodeModel codeModel;

        LIC_AUDIT(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (CREATE.getCode().equals(code)) {
                labelString = CREATE.getLabel();
            } else if (SUBMIT.getCode().equals(code)) {
                labelString = SUBMIT.getLabel();
            } else if (PASS.getCode().equals(code)) {
                labelString = PASS.getLabel();
            } else if (REFUSED.getCode().equals(code)) {
                labelString = REFUSED.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 许可证有效状态:LIC_VALID
     */
    public enum LIC_VALID {
        /**
         * INVALID:未生效
         */
        INVALID(new CodeModel("INVALID", "未生效")),
        /**
         * VALID:有效
         */
        VALID(new CodeModel("VALID", "有效")),
        /**
         * OVERDUE:过期
         */
        OVERDUE(new CodeModel("OVERDUE", "过期"));

        private CodeModel codeModel;

        LIC_VALID(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (INVALID.getCode().equals(code)) {
                labelString = INVALID.getLabel();
            } else if (VALID.getCode().equals(code)) {
                labelString = VALID.getLabel();
            } else if (OVERDUE.getCode().equals(code)) {
                labelString = OVERDUE.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 核准经营方式:LIC_MODE
     */
    public enum LIC_MODE {
        /**
         * COLLECTION:危险废物收集经营
         */
        COLLECTION(new CodeModel("COLLECTION", "危险废物收集经营")),
        /**
         * INCLUDEALL:危险废物收集、贮存、处置综合经营
         */
        INCLUDEALL(new CodeModel("INCLUDEALL", "危险废物收集、贮存、处置综合经营"));

        private CodeModel codeModel;

        LIC_MODE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (COLLECTION.getCode().equals(code)) {
                labelString = COLLECTION.getLabel();
            } else if (INCLUDEALL.getCode().equals(code)) {
                labelString = INCLUDEALL.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 危废处置方式:DISPOSE_TYPE
     */
    public enum DISPOSE_TYPE {
        /**
         * R1:作为燃料（直接燃烧除外）或以其他方式产生能量
         */
        R1(new CodeModel("R1", "作为燃料（直接燃烧除外）或以其他方式产生能量")),
        /**
         * R2:溶剂回收/再生（如蒸馏、萃取等）
         */
        R2(new CodeModel("R2", "溶剂回收/再生（如蒸馏、萃取等）")),
        /**
         * R3:再循环/再利用不是用作溶剂的有机物
         */
        R3(new CodeModel("R3", "再循环/再利用不是用作溶剂的有机物")),
        /**
         * R4:再循环/再利用金属和金属化合物
         */
        R4(new CodeModel("R4", "再循环/再利用金属和金属化合物")),
        /**
         * R5:再循环/再利用其他无机物
         */
        R5(new CodeModel("R5", "再循环/再利用其他无机物")),
        /**
         * R6:再生酸或碱
         */
        R6(new CodeModel("R6", "再生酸或碱")),
        /**
         * R7:回收污染减除剂的组分
         */
        R7(new CodeModel("R7", "回收污染减除剂的组分")),
        /**
         * R8:回收催化剂组分
         */
        R8(new CodeModel("R8", "回收催化剂组分")),
        /**
         * R9:废油再提炼或其他废油的再利用
         */
        R9(new CodeModel("R9", "废油再提炼或其他废油的再利用")),
        /**
         * R15:其他
         */
        R15(new CodeModel("R15", "其他")),
        /**
         * D1:填埋
         */
        D1(new CodeModel("D1", "填埋")),
        /**
         * D9:物理化学处理（如蒸发，干燥、中和、沉淀等），不包括填埋或焚烧前的预处理
         */
        D9(new CodeModel("D9", "物理化学处理（如蒸发，干燥、中和、沉淀等），不包括填埋或焚烧前的预处理")),
        /**
         * D10:焚烧
         */
        D10(new CodeModel("D10", "焚烧")),
        /**
         * D16:其他
         */
        D16(new CodeModel("D16", "其他")),
        /**
         * C1:水泥窑共处置
         */
        C1(new CodeModel("C1", "水泥窑共处置")),
        /**
         * C2:生产建筑材料
         */
        C2(new CodeModel("生产建筑材料", "生产建筑材料")),
        /**
         * C3:清洗（包装容器）
         */
        C3(new CodeModel("C3", "清洗（包装容器）")),
        /**
         * Y10:医疗废物焚烧
         */
        Y10(new CodeModel("Y10", "医疗废物焚烧")),
        /**
         * Y11:医疗废物高温蒸汽处理
         */
        Y11(new CodeModel("Y11", "医疗废物高温蒸汽处理")),
        /**
         * Y12:医疗废物化学消毒处理
         */
        Y12(new CodeModel("Y12", "医疗废物化学消毒处理")),
        /**
         * Y13:医疗废物微波消毒处理
         */
        Y13(new CodeModel("Y13", "医疗废物微波消毒处理")),
        /**
         * Y16:医疗废物其他处置方式
         */
        Y16(new CodeModel("Y16", "医疗废物其他处置方式")),
        /**
         * G21:干法解毒
         */
        G21(new CodeModel("G21", "干法解毒")),
        /**
         * Flammable:湿法解毒
         */
        G22(new CodeModel("G22", "湿法解毒")),
        /**
         * G23:烧结炼铁
         */
        G23(new CodeModel("G23", "烧结炼铁")),
        /**
         * G24:生产水泥
         */
        G24(new CodeModel("G24", "生产水泥")),
        /**
         * G29:其他
         */
        G29(new CodeModel("G29", "其他"));

        private CodeModel codeModel;

        DISPOSE_TYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (R1.getCode().equals(code)) {
                labelString = R1.getLabel();
            } else if (R2.getCode().equals(code)) {
                labelString = R2.getLabel();
            } else if (R3.getCode().equals(code)) {
                labelString = R3.getLabel();
            } else if (R4.getCode().equals(code)) {
                labelString = R4.getLabel();
            } else if (R5.getCode().equals(code)) {
                labelString = R5.getLabel();
            } else if (R6.getCode().equals(code)) {
                labelString = R6.getLabel();
            } else if (R7.getCode().equals(code)) {
                labelString = R7.getLabel();
            } else if (R8.getCode().equals(code)) {
                labelString = R8.getLabel();
            } else if (R9.getCode().equals(code)) {
                labelString = R9.getLabel();
            } else if (R15.getCode().equals(code)) {
                labelString = R15.getLabel();
            } else if (D1.getCode().equals(code)) {
                labelString = D1.getLabel();
            } else if (D9.getCode().equals(code)) {
                labelString = D9.getLabel();
            } else if (D10.getCode().equals(code)) {
                labelString = D10.getLabel();
            } else if (D16.getCode().equals(code)) {
                labelString = D16.getLabel();
            } else if (C1.getCode().equals(code)) {
                labelString = C1.getLabel();
            } else if (C2.getCode().equals(code)) {
                labelString = C2.getLabel();
            } else if (C3.getCode().equals(code)) {
                labelString = C3.getLabel();
            } else if (Y10.getCode().equals(code)) {
                labelString = Y10.getLabel();
            } else if (Y11.getCode().equals(code)) {
                labelString = Y11.getLabel();
            } else if (Y12.getCode().equals(code)) {
                labelString = Y12.getLabel();
            } else if (Y13.getCode().equals(code)) {
                labelString = Y13.getLabel();
            } else if (Y16.getCode().equals(code)) {
                labelString = Y16.getLabel();
            } else if (G21.getCode().equals(code)) {
                labelString = G21.getLabel();
            } else if (G22.getCode().equals(code)) {
                labelString = G22.getLabel();
            } else if (G23.getCode().equals(code)) {
                labelString = G23.getLabel();
            } else if (G24.getCode().equals(code)) {
                labelString = G24.getLabel();
            } else if (G29.getCode().equals(code)) {
                labelString = G29.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 委托处理计划-报价方式:QUOTE_TYPE
     */
    public enum QUOTE_TYPE {
        /**
         * TOTAL_QUOTE:整体报价
         */
        TOTAL_QUOTE(new CodeModel("TOTAL_QUOTE", "整体报价")),
        /**
         * DETAIL_QUOTE:明细报价
         */
        DETAIL_QUOTE(new CodeModel("INCLUDEALL", "明细报价"));

        private CodeModel codeModel;

        QUOTE_TYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (TOTAL_QUOTE.getCode().equals(code)) {
                labelString = TOTAL_QUOTE.getLabel();
            } else if (DETAIL_QUOTE.getCode().equals(code)) {
                labelString = DETAIL_QUOTE.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 委托处理计划-发布信息状态:DISPLAN_RTYPE
     */
    public enum DISPLAN_RTYPE {
        /**
         * RELEASE:已发布
         */
        RELEASE(new CodeModel("RELEASE", "已发布")),
        /**
         * ACCEPT:已确认
         */
        ACCEPT(new CodeModel("ACCEPT", "已确认"));

        private CodeModel codeModel;

        DISPLAN_RTYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (RELEASE.getCode().equals(code)) {
                labelString = RELEASE.getLabel();
            } else if (ACCEPT.getCode().equals(code)) {
                labelString = ACCEPT.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 委托处理计划-应答信息状态:DISPLAN_ATYPE
     */
    public enum DISPLAN_ATYPE {
        /**
         * SUBMIT:已提交
         */
        SUBMIT(new CodeModel("RELEASE", "已提交")),
        /**
         * ACCEPT:已确认
         */
        ACCEPT(new CodeModel("ACCEPT", "已确认"));

        private CodeModel codeModel;

        DISPLAN_ATYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (SUBMIT.getCode().equals(code)) {
                labelString = SUBMIT.getLabel();
            } else if (ACCEPT.getCode().equals(code)) {
                labelString = ACCEPT.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 用户/企业事件类型:USER_EVENT_TYPE
     */
    public enum USER_EVENT_TYPE {
        /**
         * CREATE:创建企业
         */
        CREATE(new CodeModel("CREATE", "创建企业")),
        /**
         * JOIN:加入企业
         */
        JOIN(new CodeModel("JOIN", "加入企业")),
        /**
         * QUIT:退出企业
         */
        QUIT(new CodeModel("QUIT", "退出企业"));

        private CodeModel codeModel;

        USER_EVENT_TYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (CREATE.getCode().equals(code)) {
                labelString = CREATE.getLabel();
            } else if (JOIN.getCode().equals(code)) {
                labelString = JOIN.getLabel();
            } else if (QUIT.getCode().equals(code)) {
                labelString = QUIT.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 用户/企业事件状态（USER_EVENT_STATUS）
     */
    public enum USER_EVENT_STATUS {
        /**
         * SUBMIT:申请已提交
         */
        SUBMIT(new CodeModel("SUBMIT", "申请已提交")),
        /**
         * PASS:申请通过
         */
        PASS(new CodeModel("PASS", "申请通过")),
        /**
         * REFUSED:申请未通过
         */
        REFUSED(new CodeModel("REFUSED", "申请未通过")),
        /**
         * REVERSED:申请已撤回
         */
        REVERSED(new CodeModel("REVERSED", "申请已撤回"));

        private CodeModel codeModel;

        USER_EVENT_STATUS(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (SUBMIT.getCode().equals(code)) {
                labelString = SUBMIT.getLabel();
            } else if (PASS.getCode().equals(code)) {
                labelString = PASS.getLabel();
            } else if (REFUSED.getCode().equals(code)) {
                labelString = REFUSED.getLabel();
            } else if (REVERSED.getCode().equals(code)) {
                labelString = REVERSED.getLabel();
            }

            return labelString;
        }
    }

    /**
     * 企业类型（ENTERPRISE_TYPE）用于标识企业在固废产业链中所处的业务节点位置
     */
    public enum ENTERPRISE_TYPE {
        /**
         * PRODUCTION:产废企业
         */
        PRODUCTION(new CodeModel("PRODUCTION", "产废企业")),
        /**
         * DISPOSITION:处置企业
         */
        DISPOSITION(new CodeModel("DISPOSITION", "处置企业")),
        /**
         * RECYCLING:综合利用企业
         */
        RECYCLING(new CodeModel("RECYCLING", "综合利用企业")),
        /**
         * IDENTIFICATION:鉴定机构
         */
        IDENTIFICATION(new CodeModel("IDENTIFICATION", "鉴定机构")),
        /**
         * TRANSPORTATION:运输企业
         */
        TRANSPORTATION(new CodeModel("REVERSED", "运输企业"));

        private CodeModel codeModel;

        ENTERPRISE_TYPE(CodeModel codeModel) {
            this.codeModel = codeModel;
        }

        public String getCode() {
            return codeModel.getCode();
        }

        public String getLabel() {
            return codeModel.getLabel();
        }

        public static String getLabel(String code) {
            String labelString = null;
            if (PRODUCTION.getCode().equals(code)) {
                labelString = PRODUCTION.getLabel();
            } else if (DISPOSITION.getCode().equals(code)) {
                labelString = DISPOSITION.getLabel();
            } else if (RECYCLING.getCode().equals(code)) {
                labelString = RECYCLING.getLabel();
            } else if (IDENTIFICATION.getCode().equals(code)) {
                labelString = IDENTIFICATION.getLabel();
            } else if (TRANSPORTATION.getCode().equals(code)) {
                labelString = TRANSPORTATION.getLabel();
            }

            return labelString;
        }
    }


}
