package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        int indexOfMax = 0;
        int maxValue = 0;
        int[][] matches = new int[first.length() + 1][second.length() + 1];
        for(int i = 1; i <= first.length(); i++){
            for(int j = 1; j <= second.length(); j++){
                if(first.charAt(i - 1) == second.charAt(j - 1)){
                    matches[i][j] = matches[i - 1][j - 1] + 1;
                    if (matches[i][j] > maxValue){
                        maxValue = matches[i][j];
                        indexOfMax = i;
                    }
                }
            }
        }
        return maxValue == 0 ?  "" : first.substring(indexOfMax - maxValue , indexOfMax);
    }
    // 12345678 и 45000   indexOfMax = 5, maxValue = 2 , substr(3, 5)
    //

    //////////////////////////////////////ОЦЕНКА ЗАТРАТ///////////////////////////////////////////////////////////
    // ресурсы затрачиваются T(m*n), n = first.length , m = second.length
    //решение за O(m * n)

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */

        static Set<String> baldaSearcher(String inputName, Set<String> words) {
            Set<String> res = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader(inputName));) {
                String str = "";
                List<ArrayList<Character>> inputData = new ArrayList<>();
                while ((str = br.readLine()) != null) {
                    inputData.add(new ArrayList<Character>(
                                    str.replaceAll(" ", "")
                                            .chars()
                                            .mapToObj(e -> (char) e)
                                            .collect(Collectors.toList())
                            )
                    );
                }
                for (String word : words) {         //O(i * j * word.length)
                    tooManyForOperators:
                    {
                        for (int i = 0; i < inputData.size(); i++) {
                            for (int j = 0; j < inputData.get(0).size(); j++) {
                                if (word.charAt(0) == inputData.get(i).get(j)
                                        && isInData(inputData,
                                        word.substring(1),
                                        i, j,
                                        new boolean[inputData.size()][inputData.get(i).size()])) {
                                    res.add(word);
                                    break tooManyForOperators;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e);
            }
            return res;
        }

        private static boolean isInData(// 3^n, так как после каждого перехода по рекурсии есть 3 варианта куда ходить
                List<ArrayList<Character>> inputData,
                String word,
        int i, int j,
        boolean[][] forbidden) {
            boolean res = false;
            if (word.length() == 0)
                return true;

            forbidden[i][j] = true;

            if (i != 0 && !forbidden[i - 1][j] ) {
                if (inputData.get(i - 1).get(j) == word.charAt(0)) {
                    res = isInData(inputData, word.substring(1), i - 1, j, forbidden);
                }
            }
            if (i + 1 < inputData.size() && !forbidden[i + 1][j] && !res) {
                if (inputData.get(i + 1).get(j) == word.charAt(0)) {
                    res = isInData(inputData, word.substring(1), i + 1, j, forbidden);
                }
            }
            if (j != 0 && !forbidden[i][j - 1]  && !res) {
                if (inputData.get(i).get(j - 1) == word.charAt(0)) {
                    res = isInData(inputData, word.substring(1), i, j - 1, forbidden);
                }
            }
            if (j + 1 < inputData.get(i).size() && !forbidden[i][j + 1]  && !res) {
                if (inputData.get(i).get(j + 1) == word.charAt(0)) {
                    res = isInData(inputData, word.substring(1), i, j + 1, forbidden);
                }
            }
            return res;
        }
}
//////////////////////////////////////ОЦЕНКА ЗАТРАТ/////////////////////////////////////////////////////////////////////
// O(i * j * 3^(word.length) * n), i и j - количество строк и столбцов в матрице букв, n - количество слов в заданном наборе
// T(i * j)