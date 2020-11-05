

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Programm {

    //рабочая дириктория
    public static String fileHtml = "" + System.getProperty("user.dir") + "\\" + new Date().getTime() + "_urlDownloader.html";
    private String url = "";


    public Programm (){
        System.out.println("Запуск программы по обработке html страниц");
        url = urlGet();
    }

    ///запуск программы по скачиванию и распознацванию страницы
    public void StartProgramm () {
        System.out.println("Начинаем скачивания страницы " + url);
        creatFileUrl(url);

    }

    //запрос url строки от пользователя
    private static String urlGet() {
        System.out.println("Пожалуйста введите URL-адресс, который необходимо скачать и распознать");
        Scanner in = new Scanner(System.in);
        System.out.print("URL : ");
        String url = in.nextLine();
        in.close();
        return url;
    }


    //создание файла в рабочей директории по ссылке
    private static void creatFileUrl(String url){

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            System.out.println("Подключение установлено успешно");
        }catch (IOException ex){
            System.out.println("Возникла проблема, не могу получить страницу");
            ex.printStackTrace();
            return;
        }

        FileWriter fileWriter;
        try {
            String textArr[] = doc.text().toString().split("[,.!?':;/[/]()\\n\\s]");
            fileWriter= new FileWriter(fileHtml, false);
            fileWriter.write(doc.html());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("html страница была успешно скачена");
            System.out.println(fileHtml);
            System.out.println("Начинаем подсчет повторений каждого слова в тексте \n");

            if(textArr.length > 0) {
                howManyWord(textArr);
                System.out.println("\nРабота программы завершена успешно!");
            }else {
                System.out.println("Работа программы завершена, не найдены слова к анализу");
            }
        }catch (IOException ex ){
            System.out.println("Ошибка сохранения файла");
            ex.printStackTrace();
        }
    }

    //подсчет через словарь повторения
    private static void howManyWord(String[] textArr) {
        Map<String, Integer> map = new HashMap<>();

        for (String word : textArr) {
            if(!word.equals("")) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(" " + entry.getKey() + " = " + entry.getValue());
        }

    }
}


