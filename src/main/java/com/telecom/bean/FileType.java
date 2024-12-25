package com.telecom.bean;

public enum FileType {

    /** JPEG */
    jpeg("FFD8FF"),

    /** PNG */
    png("89504E47"),

    /** GIF */
    gif("47494638"),

    /** TIFF */
    tiff("49492A00"),

    /** Windows bitmap */
    bmp("424D"),

    /** CAD */
    dwg("41433130"),

    /** Adobe photoshop */
    psd("38425053"),

    /** Rich Text Format */
    rtf("7B5C727466"),

    /** XML */
    xml("3C3F786D6C"),

    /** HTML */
    html("68746D6C3E"),

    /** Outlook Express */
    dbx("CFAD12FEC5FD746F "),

    /** Outlook */
    pst("2142444E"),

    /** Microsoft Excel */
    xls("D0CF11E0"),
    
    /** Microsoft Word */
    doc("D0CF11E0"),

    /** Microsoft Access */
    mdb("5374616E64617264204A"),

    /** Word Perfect */
    wpb("FF575043"),

    /** Adobe Acrobat */
    pdf("255044462D312E"),

    /** Windows Password */
    pwl("E3828596"),

    /** ZIP Archive */
    zip("504B0304"),

    /** ARAR Archive */
    rar("52617221"),

    /** WAVE */
    wav("57415645"),

    /** AVI */
    avi("41564920"),

    /** Real Audio */
    ram("2E7261FD"),

    /** Real Media */
    rm("2E524D46"),

    /** Quicktime */
    mov("6D6F6F76"),

    /** Windows Media */
    asf("3026B2758E66CF11"),

    /** MIDI */
    mid("4D546864");

    private String value = "";

    private FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
