public class UsersModel {
    private final String period;
    private final String campus;
    private final String roles;
    private final String userKey;
    private final String modality;
    private final Integer grade;
    private final String courseKey;

    public UsersModel(String data) {
        String[] parts = data.split("\\|");
        String[] keyword = parts[0].split("_");
        period = keyword[0];
        campus = keyword[1];
        modality = keyword[keyword.length - 2];
        grade = keyword.length == 10 ? 2 : 1;
        userKey = parts[1];
        roles = parts[2];
        courseKey = (grade == 2 ? (keyword[keyword.length - 4] + "_") : "") + keyword[keyword.length - 3];
    }

    @Override
    public String toString() {
        return "UsersModel{" +
                "period='" + period + '\'' +
                ", campus='" + campus + '\'' +
                ", roles='" + roles + '\'' +
                ", userKey='" + userKey + '\'' +
                ", modality='" + modality + '\'' +
                ", grade=" + grade +
                ", courseKey='" + courseKey + '\'' +
                '}';
    }
}
