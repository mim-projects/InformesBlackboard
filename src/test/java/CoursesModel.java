public class CoursesModel {
    private final String name;
    private final String period;
    private final String campus;
    private final String modality;
    private final Integer grade;
    private final String courseKey;

    public CoursesModel(String data) {
        String[] parts = data.split("\\|");
        String[] keyword = parts[0].split("_");
        period = keyword[0];
        campus = keyword[1];
        modality = keyword[keyword.length - 2];
        grade = keyword.length == 10 ? 2 : 1;
        name = parts[2].split("\\(")[0];
        courseKey = (grade == 2 ? (keyword[keyword.length - 4] + "_") : "") + keyword[keyword.length - 3];
    }

    @Override
    public String toString() {
        return "CoursesModel{" +
                "name='" + name + '\'' +
                ", period='" + period + '\'' +
                ", campus='" + campus + '\'' +
                ", modality='" + modality + '\'' +
                ", grade=" + grade +
                ", courseKey='" + courseKey + '\'' +
                '}';
    }
}
