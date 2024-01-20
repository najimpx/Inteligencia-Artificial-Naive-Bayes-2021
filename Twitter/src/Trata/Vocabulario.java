package Trata;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;


public class Vocabulario {
	HashMap<Integer, String> guarda = new HashMap<Integer, String>();
    LinkedList<String> NewDataset = new LinkedList<String>();
    BuscaBinaria bb = new BuscaBinaria();

	public LinkedList <String> TiraRepetido(String nomearq){
        int preLimpeza;
        int posLimpeza;
        try (FileReader reader = new FileReader(nomearq);
               BufferedReader br = new BufferedReader(reader)) {

               // read line by line
               String line;
               String temp = new String();
               
               System.out.println("Gravando palavras do arquivo na linkedlist...");
               while ((line = br.readLine()) != null) {
            	   //transforma tudo em lower case
            	   line = line.toLowerCase();
            	   String[] partes = line.split("[\t,'.;?!\")(#*:(+/-]+");
            	   //frase (ignorar as virgulas após o 4)
            	   for(int x = 3; x < partes.length; x++) {
            		   if (partes[x].startsWith("\"")) {
            			   temp += partes[x].substring("\"".length());
            		   }
            		   else if(partes[x].startsWith("\"\"")) {
	           			   temp += partes[x].substring("\"\"".length());
	           		   }
            		   else if(x ==  partes.length -1) {
            			   temp += partes[x];
	            	   }
	            	   else {
	            		   temp += partes[x]+" ";
	            	   }
            	   }
            	   
            	   String[] palavras = temp.split(" "); // temp é a frase inteira
            	   for(int j =0; j < palavras.length;j++) {
            		   if(palavras[j].length() > 0) {
            			   NewDataset.add(palavras[j]);
            		   }
            	   }
            	   temp = "";
               };
               

               System.out.println("Tamanho da LinkedList: " + NewDataset.size());
               preLimpeza = NewDataset.size();

               Set<String> set = new HashSet<>(NewDataset);
               NewDataset.clear();
               NewDataset.addAll(set);
               System.out.println("Novo Tamanho do LinkedList: " + NewDataset.size());
               posLimpeza = NewDataset.size();
               System.out.println("REDUÇÃO DE " + ((posLimpeza - preLimpeza)*-1) + " PALAVRAS!!!");

            	   // tira redundancia

            
               reader.close();
		} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
		}
        System.out.println("Remoção de duplicadas finalizada!");
		return NewDataset;
	}
	
	
	public void escreveVocab(LinkedList<String> palavras) throws IOException {
		System.out.println("Escrevendo vocabulario....");
		System.out.println("Gravando palavras na string de regitro para txt: ");
		BufferedWriter reescreve = new BufferedWriter(new FileWriter("vocabulario.txt"));
		reescreve.close();
		Writer output;
		output = new BufferedWriter(new FileWriter("vocabulario.txt", true));
		for(int i = 0; i < palavras.size(); i++) {
			output.append(palavras.get(i)+"\n");
		}
		output.close();
		
		System.out.println("acabou");
	}
	
	public LinkedList<String> RemoveNeutro() {

	    LinkedList<String> neutro = new LinkedList<String>();
        try (FileReader reader = new FileReader("vocabulario.txt");
            BufferedReader br = new BufferedReader(reader)) {
        	String line;
            while ((line = br.readLine()) != null) {
    			   if(!line.startsWith("@") && !line.startsWith("http") && !Character.isDigit(line.charAt(0))) {
    				   neutro.add(line);
    			   }
    		}
        }
        catch (IOException e) {
			System.err.format("IOException: %s%n", e);
        }
		return neutro;
	}
	
	public LinkedList <String> TiraRepetidoStop(String nomearq){
        int preLimpeza;
        int posLimpeza;
        List<String> stopwords = new ArrayList<String>();
        try (FileReader reader = new FileReader("stopwords_en.txt");
        	BufferedReader br = new BufferedReader(reader)) {
        	String line;
        	 while ((line = br.readLine()) != null) {
        		 stopwords.add(line);
        	 }
        }
        catch (IOException e) {
        	System.err.format("IOException: %s%n", e);
        }
        try (FileReader reader = new FileReader(nomearq);
               BufferedReader br = new BufferedReader(reader)) {

               // read line by line

               String line;
               String temp = new String();
               
               System.out.println("Gravando palavras do arquivo na linkedlist...");
               while ((line = br.readLine()) != null) {
            	   //transforma tudo em lower case
            	   line = line.toLowerCase();
            	   String[] partes = line.split("[\t,'.;?!\")(#*:(+/-]+");
            	   //frase (ignorar as virgulas após o 4)
            	   for(int x = 3; x < partes.length; x++) {
            		   if (partes[x].startsWith("\"")) {
            			   temp += partes[x].substring("\"".length());
            		   }
            		   else if(partes[x].startsWith("\"\"")) {
	           			   temp += partes[x].substring("\"\"".length());
	           		   }
            		   else if(x ==  partes.length -1) {
            			   temp += partes[x];
	            	   }
	            	   else {
	            		   temp += partes[x]+" ";
	            	   }
            	   }
            	   
            	   String[] palavras = temp.split(" "); // temp é a frase inteira
            	   for(int j =0; j < palavras.length;j++) {
            		   if(palavras[j].length() > 0 && !stopwords.contains(palavras[j])) {
            			   NewDataset.add(palavras[j]);
            		   }
            	   }
            	   temp = "";
               };
               

               System.out.println("Tamanho da LinkedList: " + NewDataset.size());
               preLimpeza = NewDataset.size();

               Set<String> set = new HashSet<>(NewDataset);
               NewDataset.clear();
               NewDataset.addAll(set);
               System.out.println("Novo Tamanho do LinkedList: " + NewDataset.size());
               posLimpeza = NewDataset.size();
               System.out.println("REDUÇÃO DE " + ((posLimpeza - preLimpeza)*-1) + " PALAVRAS!!!");

               
            	   // tira redundancia

               
               reader.close();
		} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
		}
        System.out.println("Remoção de duplicadas finalizada!");
		return NewDataset;
	}
}