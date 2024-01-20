package NaiveBayes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import java.lang.Math;

public abstract class Esqueleto {
	
	public void Distribui(String nomearq) {
        
		ArrayList<Palavras> termos = new ArrayList<Palavras>();
		List<String> palavrasPos = new ArrayList<String>();
		List<String> palavrasNeg = new ArrayList<String>();
		
		//List de cada String positivo ou negativo
		palavrasPos = palavrasPositivas(nomearq);
		palavrasNeg = palavrasNegativas(nomearq);
		
		//cria uma lista para o objeto de cada palavra
		termos = criaPalavraMap(palavrasPos, palavrasNeg);
		escreveObjeto(termos,"palavradata.ser");
	}
	
	public double Bayes (String nomearq, String treinoarq) {
		int acertos = 0;
		int erros = 0;
		Map<String,Palavras> ClassPalavras = leObjetoMap();
		List<Palavras> ListaPalavras = leObjetoList();
        int PalNeg = nPalneg(ListaPalavras);
        int Palpos = nPalpos(ListaPalavras);
        double distNeg = DistrNeg(treinoarq);
        double distPos = DistrPos(treinoarq);
        
		boolean resultadoFinal = false;

        try (FileReader reader = new FileReader(nomearq);
        BufferedReader br = new BufferedReader(reader)) {
        	String line;
        	String temp = new String();
        	int Vpos = 0;
        	int Vneg = 0;
        	int Fpos = 0;
        	int Fneg = 0;
         	boolean sentimento = false;
        	while ((line = br.readLine()) != null) {
        		double classificadorP = 0;
        		double classificadorN = 0;
        		
            	line = line.toLowerCase();
             	String[] partes = line.split("[\t,'.;?!\")(#*:(+/-]+");
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
            	String[] palavrasIndv = temp.split(" ");
                double tempN = 1;
                double tempP = 1;
        		for(int i = 0;i < palavrasIndv.length;i++) {
	        		Palavras Paltemp = ClassPalavras.get(palavrasIndv[i]);
	        		if(Paltemp != null) {
	        			if(Paltemp.repeticoesN != 0) {
	        				tempN *= ((double)Paltemp.repeticoesN/(double)PalNeg);
	        			}
	        			if(Paltemp.repeticoesP != 0) {
	        				tempP *= ((double)Paltemp.repeticoesP/(double)Palpos);
	        			}
	        		}
        		}
                        
                classificadorN = tempN * distNeg;
                classificadorP = tempP * distPos;
        		if(classificadorP > classificadorN && tempN != 1) {
        			resultadoFinal = true;
        		}
        		else if(tempN == 1) {
        			resultadoFinal = false;
        		}
        		if (classificadorP < classificadorN && tempP != 1) {
        			resultadoFinal = false;
        		}
        		else if(tempP != 1) {
        			resultadoFinal = true;
        		}
        		
        		if(resultadoFinal == sentimento) {
        			if(sentimento == true) {
        				Vpos++;
        			}
        			if(sentimento == false) {
        				Vneg++;
        			}
        			acertos++;
        		}
        		else {
        			erros++;
        			if(sentimento == true) {
        				Fneg++;
        			}
        			if(sentimento == false) {
        				Fpos++;
        			}
        		}
        		temp = "";
			}
            System.out.println("acertos: "+acertos);
            System.out.println("erros: "+erros);
            System.out.println("Verdadeiro Positivo :"+Vpos);
            System.out.println("Verdadeiro Negativo :"+Vneg);
            System.out.println("Falso Positivo :"+Fpos);
            System.out.println("Falso Negativo :"+Fneg);
        }
		catch (IOException e) {
    		System.err.format("IOException: %s%n", e);
        }
        return (double)erros/((double)erros+(double)acertos);
	}
	
	public void escreveSaida(double mediaErro,String Treinoarq) {
		System.out.println((double)nFrasesTreino(Treinoarq));
		double erroPadrao = Math.sqrt((mediaErro*((double)(1-mediaErro)))/(double)nFrasesTreino(Treinoarq));
		try {
			FileWriter writer = new FileWriter("Saida.txt");
			
			writer.write("Erro Padrao 10-fold : "+ erroPadrao+"\n");
			writer.write("O erro verdadeiro vai ficar entre : "+ (mediaErro - 1.96 * erroPadrao)+" e "+(mediaErro + 1.96 * erroPadrao)+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract double LaPlace(double alfa, int qtdPositivos, int Total, int TotalACertados );
	
	protected abstract double DistrPos(String nomearq);
	
	protected abstract double DistrNeg(String nomearq);
	
	protected abstract List<String> palavrasPositivas(String nomearq);
	
	protected abstract List<String> palavrasNegativas(String nomearq);
	
	protected abstract ArrayList<Palavras> criaPalavraMap(List<String> palavrasPos, List<String> palavrasNeg);
	
	protected abstract void escreveObjeto(ArrayList<Palavras> termos, String nomearq);
	
	protected abstract Map<String,Palavras> leObjetoMap();
	
	protected abstract List<Palavras> leObjetoList();
	
	protected abstract int nPalpos(List<Palavras> ListaPal);
	
	protected abstract int nPalneg(List<Palavras> ListaPal);
	
	protected abstract int nFrasesTreino(String Treinoarq);
}
