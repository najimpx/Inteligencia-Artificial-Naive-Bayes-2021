package NaiveBayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.stream.*;
import java.util.*;
import java.util.function.*;

public class Corpo extends Esqueleto{
	
	protected double DistrPos(String nomearq)
	{
		double total = 0;
		double positivo = 0;
		double Probpositivo = 0;
		
        try (FileReader reader = new FileReader(nomearq);
            BufferedReader br = new BufferedReader(reader)) {

        	String line;
            while ((line = br.readLine()) != null) {
            	String[] partes = line.split(",");
    			if(partes[1].equals("1")) {
    				positivo++;
    			}
    			total++;
    		}
        }
        catch (IOException e) {
			System.err.format("IOException: %s%n", e);
        }
        Probpositivo = positivo/total;
		return Probpositivo;
	}
	
	protected double DistrNeg(String nomearq) {
		double total = 0;
		double negativo = 0;
		double Probnegativo = 0;
		
        try (FileReader reader = new FileReader(nomearq);
            BufferedReader br = new BufferedReader(reader)) {

        	String line;
            while ((line = br.readLine()) != null) {
            	
            	String[] partes = line.split(",");
    			if(partes[1].equals("0")){
    				negativo++;
    			}
    			total++;
    			
    		}
        }
        catch (IOException e) {
			System.err.format("IOException: %s%n", e);
        }
		Probnegativo = (double)negativo/total;
		return Probnegativo;
	}
	
	
	protected List<String> palavrasPositivas(String nomearq){
		List<String> positivo = new ArrayList<String>();
		
	     try (FileReader reader = new FileReader(nomearq);
	                BufferedReader br = new BufferedReader(reader)) {
	            	String line;
	             	String temp = new String();
	                while ((line = br.readLine()) != null) {
	                	line = line.toLowerCase();
		             	String[] partes = line.split("[\t,'.;?!\")(#*:(+/-]+");
		             	boolean sentimento = false;
		             	if(partes[1].equals("1")) {
		             		sentimento = true;
		             	}
		             	else if(partes[1].equals("0")) {
		             		sentimento = false;
		             	}
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
		            	
		            	String[] palavrasIndv = temp.split(" "); // temp é a frase inteira
		            	for(int z = 0; z < palavrasIndv.length; z++) {
		            		if(sentimento == true) {
		            			positivo.add(palavrasIndv[z]);
		            		}
		            	}
		            	temp = "";
	        		}
	            }
	    catch (IOException e) {
	    	System.err.format("IOException: %s%n", e);
	    }
		
		return positivo;
	}

	protected List<String> palavrasNegativas(String nomearq){
		List<String> negativo = new ArrayList<String>();
		
	     try (FileReader reader = new FileReader(nomearq);
	                BufferedReader br = new BufferedReader(reader)) {
	            	String line;
	            	String temp = new String();
	             	
	                while ((line = br.readLine()) != null) {
	                	line = line.toLowerCase();
		             	String[] partes = line.split("[\t,'.;?!\")(#*:(+/-]+");
		             	boolean sentimento = false;
		             	if(partes[1].equals("1")) {
		             		sentimento = true;
		             	}
		             	else if(partes[1].equals("0")) {
		             		sentimento = false;
		             	}
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
		            	
		            	String[] palavrasIndv = temp.split(" "); // temp é a frase inteira
		            	for(int z = 0; z < palavrasIndv.length; z++) {
		            		if(sentimento == false) {
		            			negativo.add(palavrasIndv[z]);
		            		}
		            	}
		            	temp = "";
	        		}
	            }
	    catch (IOException e) {
	    	System.err.format("IOException: %s%n", e);
	    }
		
		return negativo;
	}
	
	protected void escreveObjeto(ArrayList<Palavras> termos, String nomearq) {
        try{
            FileOutputStream writeData = new FileOutputStream(nomearq);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(termos);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	protected Map<String,Palavras> leObjetoMap(){
		ArrayList<Palavras> palavratemp = new ArrayList<Palavras>();
		Map<String,Palavras> palavraMap = new HashMap<String,Palavras>();
        try{
            FileInputStream readData = new FileInputStream("palavradata.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            palavratemp = (ArrayList<Palavras>) readStream.readObject();
            for(int i = 0; i < palavratemp.size();i++) {
            	palavraMap.put(palavratemp.get(i).vocabulo,palavratemp.get(i));
            }
            readStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return palavraMap;
	}
	
	protected List<Palavras> leObjetoList(){
		ArrayList<Palavras> palavratemp = new ArrayList<Palavras>();
        try{
            FileInputStream readData = new FileInputStream("palavradata.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            palavratemp = (ArrayList<Palavras>) readStream.readObject();
            readStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return palavratemp;
	}
	
	protected ArrayList<Palavras> criaPalavraMap(List<String> palavrasPos, List<String> palavrasNeg){
		ArrayList<Palavras> termos = new ArrayList<Palavras>();
        try (FileReader reader = new FileReader("vocabulario.txt");
                BufferedReader br = new BufferedReader(reader)) {

            	String line;
            	boolean primeiro = true;
                while ((line = br.readLine()) != null) {
                	if(primeiro == true) {
                		primeiro = false;
                		continue;
                	}
                	Palavras conteudo = new Palavras();
                	conteudo.vocabulo = line;
                	conteudo.repeticoes = 1;
                	termos.add(conteudo);
        		}
            }
        catch (IOException e) {
    		System.err.format("IOException: %s%n", e);
        }
        Map<String, Long> freqP = new HashMap<>();
        for (String s: palavrasPos) {
            freqP.merge(s, 1L, Long::sum);
        }
        Map<String, Long> freqN = new HashMap<>();
        for (String s: palavrasNeg) {
            freqN.merge(s, 1L, Long::sum);
        }
		for(int i = 0; i< termos.size() ;i ++) {
			if(freqP.get(termos.get(i).vocabulo) != null) {
				termos.get(i).repeticoesP = freqP.get(termos.get(i).vocabulo).intValue();
			}
			else{
				termos.get(i).repeticoesP = 0;
			}
			if(freqN.get(termos.get(i).vocabulo) != null) {
				termos.get(i).repeticoesN = freqN.get(termos.get(i).vocabulo).intValue();
			}
			else {
				termos.get(i).repeticoesN = 0;
			}
			termos.get(i).repeticoes = termos.get(i).repeticoesP + termos.get(i).repeticoesN;
			
			termos.get(i).Ppos = (double)termos.get(i).repeticoesP/termos.get(i).repeticoes;
			termos.get(i).Pneg = (double)termos.get(i).repeticoesN/termos.get(i).repeticoes;
			
		}
		return termos;
	}
	
    protected double LaPlace(double alfa, int qtdSinalPreditos, int Total, int TotalACertados) {
        
	    return (TotalACertados + alfa)/(qtdSinalPreditos + alfa * Total);
	    
    }
    
	protected int nPalpos(List<Palavras> vocabulario) {
		int temp = 0;
		for(int i = 0; i<vocabulario.size(); i++) {
			temp += vocabulario.get(i).repeticoesP;
		}
		return temp;
	}
	
	protected int nPalneg(List<Palavras> vocabulario) {
		int temp = 0;
		for(int i = 0; i<vocabulario.size(); i++) {
			temp += vocabulario.get(i).repeticoesP;
		}
		return temp;
	}
	protected int nFrasesTreino(String Treinoarq) {
		int linhas = 0;
        try (FileReader reader = new FileReader(Treinoarq);
                BufferedReader br = new BufferedReader(reader)) {
                while ((br.readLine()) != null) {
                	linhas++;
        		}
            }
            catch (IOException e) {
    			System.err.format("IOException: %s%n", e);
            }
		return linhas;
	}
}

