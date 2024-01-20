package Trata;

import java.util.LinkedList;

public class BuscaBinaria {
												// limpo, palavra[i]
	public boolean Palavras(LinkedList<String> LinkedList, String Palavra) {
		//int i = 1;
		int min = 0;
		int max = LinkedList.size() - 1;  
		int mid;
		//System.out.println("BuscaBinaria");
	//	 System.out.println("min: " + min);
		// System.out.println("max: " + max);
		if(max == -1) {return false;};
		while (min <= max) {
		//	System.out.println(i);
			//i++;
			// System.out.println("min: " + min);
			// System.out.println("max: " + max);
			mid = (min + max) / 2;
			// System.out.println("mid: " + mid);
			// System.out.println("compare: " + X.get(mid).compareTo(Y));
			if (LinkedList.get(mid).compareTo(Palavra) < 0) {
				min = mid + 1;
			} else if (LinkedList.get(mid).compareTo(Palavra) > 0) {
				max = mid - 1;
			} else {
			//	System.out.println("achou");
		//		System.out.println(LinkedList.get(mid) + " " + Palavra);
				return true;
			}

		}
		;
		return false;

	};
};
