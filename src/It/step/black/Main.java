package It.step.black;

import java.util.Scanner;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {new Main(); }

    private String[] action = new String[]{
            "Добавить элемент",
            "Удалить элемент",
            "Удалить все элементы",
            "Найти элемент",
            "Вывести все элементы",
            "Выйти"
    };
    private Main() {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            showChoices();
            int choice = scan.nextInt() - 1;
            switch (choice) {
                case 0:
                    out.println("Введите элемент");
                    try {
                        Tree.addNode(scan.next().charAt(0));
                        out.println("Элемент добавлен");
                    } catch (Exception e) { e.printStackTrace(); }
                    break;
                case 1:
                    out.println("Введите элемент, который нужно удалить");
                    Tree.removeNode(scan.next().charAt(0));
                    break;
                case 2:
                    Tree.removeAll();
                    out.println("Все элементы были удалены");
                    break;
                case 3:
                    out.println("Введите элемент");
                    Node res = Tree.find(scan.next().charAt(0));
                    if (res == null) {
                        out.println("Такого нет");
                    } else {
                        out.println(res.toString());
                    }
                    break;
                case 4:
                    out.println(Tree.serialize());
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }
    private void showChoices() {
        out.println("Что вы хотите сделать?");
        for (int i = 0; i < action.length; i++)
            out.println(" " + (i + 1) + ". " + action[i]);
    }
}