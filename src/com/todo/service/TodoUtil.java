package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� �߰�]\n" + "���� > ");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��Ǿ�� �ȵ˴ϴ�:(");
			return;
		}
		System.out.print("ī�װ� > ");
		category = sc.next();
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		System.out.print("�������� > ");
		due_date = sc.nextLine().trim();

		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.printf("�߰��Ϸ� :)");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� ����]\n" + "������ �׸��� ��ȣ > ");
		int index = sc.nextInt();
		if (index > l.getCount()) {
			System.out.println("���� ��ȣ�Դϴ� :(");
			return;
		}
		System.out.println((index) + ". " + l.getItem(index - 1).toString());
		System.out.print("�׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
		String yesorno = sc.next();
		if (yesorno.equals("y")) {
			l.deleteItem(l.getItem(index - 1));
			System.out.printf("�����Ϸ�:)");
		}
	}

	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.printf("[�׸� ����]\n" + "������ �׸��� ��ȣ > ");
		int index = sc.nextInt();
		if (index > l.getCount()) {
			System.out.println("���� ��ȣ�Դϴ�:(");
			return;
		}

		System.out.print("���ο� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��Ǿ �ȵ˴ϴ�:(");
			return;
		}
		System.out.print("�� ī�װ� > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.print("���ο� ���� > ");
		String new_desc = sc.nextLine().trim();
		System.out.print("�� �������� > ");
		String new_due_date = sc.nextLine().trim();

		l.deleteItem(l.getItem(index - 1));
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		l.addItem(t);
		System.out.println("�����Ϸ�;)");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d]\n", l.getCount());
//		for (TodoItem item : l.getList()) {
//			System.out.println(item.toString());
//		}
		for (int i = 0; i < l.getCount(); i++) {
			System.out.println((i + 1) + ". " + l.getItem(i).toString());
		}
	}

	public static void listCateAll(TodoList l) {
		Set<String> clist = new HashSet<String>();

		for (TodoItem c : l.getList()) {
			clist.add(c.getCategory());
		}

		Iterator it = clist.iterator();
		while (it.hasNext()) {
			String s = (String) it.next();
			System.out.print(s);
			if (it.hasNext())
				System.out.print(" / ");
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", clist.size());
	}

	public static boolean isExistCategory(List<String> clist, String cate) {
		for (String c : clist) if(c.equals(cate)) return true;
		return false;
	}

	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (int i = 0; i < l.getCount(); i++) {
			if (l.getItem(i).getTitle().contains(keyword) || l.getItem(i).getCategory().contains(keyword)) {
				System.out.println((i + 1) + ". " + l.getItem(i).toString());
				count++;
			}
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (int i = 0; i < l.getCount(); i++) {
			if (l.getItem(i).getCategory().contains(cate)) {
				System.out.println((i + 1) + ". " + l.getItem(i).toString());
				count++;
			}
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

	public static void saveList(TodoList l, String filename) {
		// TODO Auto-generated method stub
		try {
			Writer w = new FileWriter(filename);

			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("��� �����Ͱ� ����Ǿ����ϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

			String oneline;
			int count = 0;
			while ((oneline = br.readLine()) != null) {
				System.out.println(oneline);
				StringTokenizer st = new StringTokenizer(oneline, "-");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title, desc, category, due_date);
				item.setCurrent_date(current_date);
				l.addItem(item);
				count++;
			}
			br.close();
			System.out.println(count + "���� �׸��� �о����ϴ�");
		} catch (FileNotFoundException e) {
			System.out.println(filename + " ������ �����ϴ�.");
			// e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
