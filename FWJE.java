import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInfoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");
        String input = scanner.nextLine();
        scanner.close();

        try {
            // Разделение введенной строки по пробелам
            String[] data = input.split(" ");

            // Проверка количества введенных данных
            if (data.length < 6) {
                throw new IllegalArgumentException("Вы ввели меньше данных, чем требуется.");
            } else if (data.length > 6) {
                throw new IllegalArgumentException("Вы ввели больше данных, чем требуется.");
            }

            // Разбор данных
            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDate = data[3];
            String phoneNumber = data[4];
            String gender = data[5];

            // Проверка даты рождения
            if (!Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", birthDate)) {
                throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается формат dd.mm.yyyy.");
            }

            // Проверка номера телефона (целое беззнаковое число)
            try {
                Long.parseLong(phoneNumber);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Номер телефона должен быть целым числом без форматирования.");
            }

            // Проверка пола (f или m)
            if (!gender.equalsIgnoreCase("f") && !gender.equalsIgnoreCase("m")) {
                throw new IllegalArgumentException("Пол должен быть указан символом 'f' или 'm'.");
            }

            // Запись в файл
            writeToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);

        } catch (IllegalArgumentException e) {
            // Обработка некорректных данных
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            // Обработка ошибок при работе с файлом
            e.printStackTrace();
        }
    }

    // Метод записи данных в файл
    private static void writeToFile(String lastName, String firstName, String middleName, String birthDate, String phoneNumber, String gender) throws IOException {
        try (FileWriter writer = new FileWriter(lastName + ".txt", true)) {
            String line = String.format("%s %s %s %s %s %s%n", lastName, firstName, middleName, birthDate, phoneNumber, gender);
            writer.write(line);
            System.out.println("Данные успешно записаны в файл: " + lastName + ".txt");
        }
    }
}
