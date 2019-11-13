package lesson1;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    private static String intTimeToString(int intTime) { //O(1)
        String res;
        Formatter f = new Formatter();
        if (intTime < 3600) {
            res = f.format("%02d:%02d:%02d ", 12, (intTime / 60), (intTime % 60)).toString();
        } else {
            res = f.format("%02d:%02d:%02d ", intTime / 3600, (intTime % 3600) / 60, (intTime % 3600) % 60).toString();
        }
        return res;
    }

    static void sortTimes(String inputName, String outputName) throws IOException {
        List<Integer> listAM = new ArrayList<>();
        List<Integer> listPM = new ArrayList<>();
        int[] curr = new int[3];

        try (BufferedReader br = new BufferedReader(new FileReader(inputName))){
            String str;
            while ((str = br.readLine()) != null) {                   //весь цикл while O(N), N - количество строк
                if (!str.matches("(\\d\\d):(\\d\\d):(\\d\\d) (AM|PM)"))
                    throw new IllegalArgumentException();
                String[] temp = str.split(" ");
                for (int i = 0; i < 3; i++)
                    curr[i] = Integer.parseInt(temp[0].split(":")[i]);
                if ((curr[0] > 12 || curr[0] < 0) || (curr[1] > 59 || curr[1] < 0) || (curr[2] > 59 || curr[2] < 0))
                    throw new IllegalArgumentException();
                int e = curr[0] % 12 * 60 * 60 + curr[1] * 60 + curr[2];
                if (temp[1].equals("AM")) {
                    listAM.add(e);                           //т.е. переводим в секунды
                } else {                                   //%12 потому что часы начинаются с 12 как с нуля
                    listPM.add(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw  new IllegalArgumentException(e);
        }
        Comparator<Integer> compare = Integer::compareTo;

        listAM.sort(compare); //O(N*log(N))
        listPM.sort(compare);   //O(N*log(N))

        try (FileWriter writer = new FileWriter(outputName)) {
            StringBuilder sb = new StringBuilder();
            for (int t : listAM) {
                sb.append(intTimeToString(t)).append("AM").append("\n");
            }
            for (int t : listPM) {
                sb.append(intTimeToString(t)).append("PM").append("\n");
            }
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    //////////////////////////////////////ОЦЕНКА ЗАТРАТ///////////////////////////////////////////////////////////
    // Time complexity O(N*log(N)) mem complexity: T(N) - 2 ArrayLists of total capacity N

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static void sortAddresses(String inputName, String outputName) {
        Map<String, List<Name>> data = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputName));
            String[] temp;
            String str;
            while ((str = br.readLine()) != null) {                               //O(log(N) * N), N - количество строк
                if (!str.toLowerCase().matches("\\S+ \\S+ - \\S+ \\d+"))
                    throw new IllegalArgumentException();
                temp = str.split(" ");
                String address = temp[3] + " " + Integer.parseInt(temp[4]);
                Name person = new Name(temp[1], temp[0]);

                if (!data.containsKey(address)) {
                    data.put(address, new ArrayList<>(Collections.singletonList(person)));                 //O(1)
                } else {
                    data.get(address).add(person);          //О(1) + О(1) = O(1)
                }
            }

        }catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }

        Comparator<String> compareAddresses = Comparator.comparing(str -> str.split(" ")[0]);
        compareAddresses = compareAddresses.thenComparing(str -> Integer.parseInt(str.split(" ")[1]));

        List<String> dataKeys = new ArrayList<>(data.keySet());
        dataKeys.sort(compareAddresses);                                                          // O(a*log(a))

        try (FileWriter writer = new FileWriter(outputName)) {
            StringBuilder sb = new StringBuilder();
            for(String a : dataKeys) {                                 //O(a * n * log(n)),
                StringBuilder s = new StringBuilder(a + " -");
                ArrayList<Name> n = new ArrayList<>(data.get(a));
                Collections.sort(n);              //O(n*log(n))
                for (Name name : n)              //O(n)
                    s.append(" ").append(name.last).append(" ").append(name.first).append(",");
                s.deleteCharAt(s.length() - 1);
                sb.append(s).append("\n");
            }
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    static class Name implements Comparable<Name> {
        private String first;
        private String last;

        Name(String first, String last) {
            this.first = first;
            this.last = last;
        }

        @Override
        public int compareTo(Name name) {
            if (last.equals(name.last)) return first.compareTo(name.first);
            else {
                if (last.compareTo(name.last) > 0)
                    return 1;
                else
                    return -1;
            }
        }
    }

    //////////////////////////////////////ОЦЕНКА ЗАТРАТ///////////////////////////////////////////////////////////
// O(a * n * log(n)), a - количество адресов, n - среднее сокисчество жильцов по адресу. ресурсоемкость T(N*n)


    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        int[] data = new int[2730 + 5000 + 1];

        try (BufferedReader br = new BufferedReader(new FileReader(inputName));) {
            String str = "";
            while ((str = br.readLine()) != null){
                if(!str.matches("-?[0-9]{1,3}\\.[0-9]")){
                    throw  new IllegalArgumentException();
                }
                int curr = (int)(Float.parseFloat(str) * 10);
                if(str.charAt(0) == '-'){
                    data[Math.abs(curr + 2730)]++;                // -273.0 лежит в индексе 0, (+-)0.0 в 2730
                } else{
                    data[curr + 2730]++;
                }
            }
            try (FileWriter writer = new FileWriter(outputName)) {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < 2730; i++){
                    if(data[i] > 0)
                        for(int j = 0; j < data[i]; j++)
                            sb.append("-").append(Math.abs((i - 2730) / 10)).append(".").
                                    append((Math.abs(i - 2730)) % 10).append("\n"); //  -0.1 лежит в 2729
                }
                for(int i = 2730; i < data.length; i++){
                    if(data[i] > 0)
                        for(int j = 0; j < data[i]; j++)
                            sb.append((i - 2730) / 10).append(".").append((i - 2730) % 10).append("\n");
                }
                writer.write(sb.toString());
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
    //////////////////////////////////////ОЦЕНКА ЗАТРАТ///////////////////////////////////////////////////////////
    //времязатраты O(N),N - колво входных значений, ресурсоемкость T(1)

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int i = 0;
        int j = first.length;
        for (int k = 0; k < second.length; k++) {
            if ((j >= second.length) || (i < first.length && first[i].compareTo(second[j]) < 0)) {
                second[k] = first[i];
                i++;
            } else {
                second[k] = second[j];
                j++;
            }
        }
    }
}

//////////////////////////////////////ОЦЕНКА ЗАТРАТ///////////////////////////////////////////////////////////
//O(n), n = second.length для времени.  ресурсозатраты T(1)
