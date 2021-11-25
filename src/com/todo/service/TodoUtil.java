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

		System.out.print("[항목 추가]\n" + "제목 > ");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복되어서는 안됩니다:(");
			return;
		}
		System.out.print("카테고리 > ");
		category = sc.next();
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();

		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.printf("추가완료 :)");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print("[항목 삭제]\n" + "삭제할 항목의 번호 > ");
		int index = sc.nextInt();
		if (index > l.getCount()) {
			System.out.println("없는 번호입니다 :(");
			return;
		}
		System.out.println((index) + ". " + l.getItem(index - 1).toString());
		System.out.print("항목을 삭제하시겠습니까? (y/n) > ");
		String yesorno = sc.next();
		if (yesorno.equals("y")) {
			l.deleteItem(l.getItem(index - 1));
			System.out.printf("삭제완료:)");
		}
	}

	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.printf("[항목 수정]\n" + "수정할 항목의 번호 > ");
		int index = sc.nextInt();
		if (index > l.getCount()) {
			System.out.println("없는 번호입니다:(");
			return;
		}

		System.out.print("새로운 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복되어선 안됩니다:(");
			return;
		}
		System.out.print("새 카테고리 > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.print("새로운 내용 > ");
		String new_desc = sc.nextLine().trim();
		System.out.print("새 마감일자 > ");
		String new_due_date = sc.nextLine().trim();

		l.deleteItem(l.getItem(index - 1));
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		l.addItem(t);
		System.out.println("수정완료;)");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d]\n", l.getCount());
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
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", clist.size());
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
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (int i = 0; i < l.getCount(); i++) {
			if (l.getItem(i).getCategory().contains(cate)) {
				System.out.println((i + 1) + ". " + l.getItem(i).toString());
				count++;
			}
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void saveList(TodoList l, String filename) {
		// TODO Auto-generated method stub
		try {
			Writer w = new FileWriter(filename);

			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("모든 데이터가 저장되었습니다.");
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
			System.out.println(count + "개의 항목을 읽었습니다");
		} catch (FileNotFoundException e) {
			System.out.println(filename + " 파일이 없습니다.");
			// e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
