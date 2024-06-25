import com.mimsoft.informesblackboard.application.utils.system.FileManager;

public class FileTest {
    public static void main(String[] args) {
//        CoursesFiles();
        UsersFiles();
    }

    private static void CoursesFiles() {
        String path = "C:\\Users\\amcod\\Desktop\\mimsoft\\SistemaInformesBlackboard\\Data\\2024-03\\POS\\20241_GRUPOS (03-01-07).txt";
        try {
            FileManager.ReadLine(path, (index, content) -> {
                if (index == 0 || content.contains("***FileFooter")) return;
                String[] data = content.trim().split("\\|");
                if (data[4].equalsIgnoreCase("y")) {
                    System.out.println(index + " >> + " + new CoursesModel(content));
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void UsersFiles() {
        String path = "C:\\Users\\amcod\\Desktop\\mimsoft\\SistemaInformesBlackboard\\Data\\2024-03\\POS\\20241_CARGAALUM (03-01-07).txt";
        try {
            FileManager.ReadLine(path, (index, content) -> {
                if (index == 0 || content.contains("***FileFooter")) return;
                String[] data = content.trim().split("\\|");
                if (data[3].equalsIgnoreCase("y")) {
                    System.out.println(index + " >> " + new UsersModel(content));
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
