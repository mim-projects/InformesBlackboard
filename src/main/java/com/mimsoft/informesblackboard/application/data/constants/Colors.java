package com.mimsoft.informesblackboard.application.data.constants;

public enum Colors {
    UABC_YELLOW("#DD971A"),
    UABC_YELLOW_1("#FCC621"),
    UABC_YELLOW_2("#FCDB75"),

    UABC_GREEN("#00723F"),
    UABC_GREEN_1("#00AA5D"),
    UABC_GREEN_2("#7BD781"),

    UABC_BLUE("#20419A"),
    UABC_BLUE_1("#2C58D2"),
    UABC_BLUE_2("#8CB2DB"),
    ;

    private final String value;

    Colors(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String[] getRGB() {
        String color = value.replace("#", "");
        String[] parts = color.split("");
        return new String[] {
                String.valueOf(Integer.parseInt(parts[0] + parts[1], 16)),
                String.valueOf(Integer.parseInt(parts[2] + parts[3], 16)),
                String.valueOf(Integer.parseInt(parts[4] + parts[5], 16))
        };
    }

    public static String[] GetAllColors() {
        return new String[] {
                UABC_YELLOW.getValue(), UABC_GREEN.getValue(), UABC_BLUE.getValue(),
        };
    }

    public static String GetColor(int index) {
        String[] colors = Colors.GetAllColors();
        return colors[index % colors.length];
    }
}
