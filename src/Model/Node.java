package src.Model;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

class Node {

	protected int capacity; // massimo numero di rettangoli inseribili nel nodo

	protected int numRectangle; // numero dei rettangoli inseriti nel nodo

	Node parent_node; // puntatore al nodo padre

	int indexParent; // indice dell'array del padre che punta al nodo in questione

	Rectangle[] rectangle; // array dei rettangoli contenuti nel nodo

	Node[] childNode; // array dei figli del nodo, contenuti nei rettangoli corrispondenti

	// verifica se il nodo è una foglia

	public boolean isLeaf() {

		if (childNode[0] == null)
			return true;

		else
			return false;

	}

	// imposta il padre

	public void setParent(Node node) {

		this.parent_node = node;

	}

	// restituisce il padre

	public Node getParent() {

		return this.parent_node;

	}

	// imposta l'indice nell'array del padre

	public void setIndex(int i) {

		this.indexParent = i;

	}

	// restituisce l'indice nell'array del padre

	public int getIndex() {

		return this.indexParent;

	}

	// imposta un rettangolo

	public void setRect(int i, Rectangle rectangle) {

		this.rectangle[i] = rectangle;

	}

	// cancella un rettangolo

	public void delRectangle(int i) {

		rectangle[i] = null;

		childNode[i] = null;

		numRectangle--;

	}

	// imposta il figlio i-esimo del nodo

	public void setChild(int i, Node node) {

		this.childNode[i] = node;

	}

	// costruttore

	public Node(int l) {

		capacity = l;

		numRectangle = 0;

		indexParent = -1;

		parent_node = null;

		rectangle = new Rectangle[capacity];

		childNode = new Node[capacity];

		for (int i = 0; i < capacity; i++) {

			childNode[i] = null;

			rectangle[i] = null;

		}

	}

	// inserisce un rettangolo r nella foglia scelta,

	// n è il nodo figlio dipendente dal rettangolo che si va a inserire

	public Node insertNewRectangle(Rectangle rectangle, Node node) {

		Node newNode;

		if (this.numRectangle < this.capacity) {

			this.rectangle[numRectangle] = rectangle; // inserisco il nuovo rettangolo

			this.childNode[numRectangle] = node; // inserisco il nuovo figlio relativo al rettangolo

			if (node != null) {

				node.setIndex(numRectangle);

				node.setParent(this);

			}

			this.numRectangle++;

			newNode = null;

		}

		else {

			newNode = split(rectangle, node);// eseguo lo splitting

		}

		return newNode;

	}

	// prende un rettangolo r che ha come figlio il nodo n e lo

	// inserisce nel nodo corrente sdoppiandolo

	protected Node split(Rectangle Now_rectangle, Node node) {

		Node addedNode;

		Rectangle rectTmp, rectMin, rectPrec;

		Dimension size, minSize;

		double area, minArea;

		int min = 0; // indice del figlio scelto per inserire il rettangolo

		int y;

		addedNode = new Node(this.capacity); // creo il nuovo nodo

		addedNode.insertNewRectangle(Now_rectangle, node); // inserisco subito il nuovo rettangolo

		// ora lego il nuovo nodo con il figlio del rettangolo che sto

		// inserendo

		if (node != null) {

			node.setParent(addedNode);

			node.setIndex(0);

		}

		// inizializzo i valori in base al primo rettangolo

		rectMin = Now_rectangle.union(rectangle[0]);

		minSize = rectMin.getSize();

		minArea = minSize.getHeight() * minSize.getWidth();

		rectPrec = Now_rectangle;

		// poi verifico che non ce ne siano altri piu' adatti

		// il primo ciclo mi serve per contare quanti rettangoli devo aggiungere

		for (int k = 1; k < ((numRectangle + 1) / 2); k++) {

			// il secondo ciclo controlla quale è il rettangolo più adatto da aggiungere

			for (int i = 1; i < numRectangle; i++) {

				// faccio la prova con un rettangolo che non sia

				// gia' stato tolto

				if (rectangle[i] != null) {

					rectTmp = rectPrec.union(rectangle[i]);

					// rectTmp e' il rettangolo che contiene i due rettangoli

					size = rectTmp.getSize();

					area = size.getHeight() * size.getWidth();

					if (area < minArea) {

						// poiche' ho trovato una coppia migliore,

						// aggiorno i dati del rettangolo minimo

						minArea = area;

						min = i;

						rectMin = rectTmp;

					}

				}

			}

			rectPrec = rectMin;

			// min e' l'indice del rettangolo da mettere insieme a r

			// inserisco subito il nuovo rettangolo

			addedNode.insertNewRectangle(this.rectangle[min], this.getChild(min));

			// elimino il rettangolo dal vecchio nodo

			rectangle[min] = null;

			// faccio ora crescere il mio rettangolo temporaneo,

			// aggiorno quindi il rettangolo precedente

			y = 0;

			while (y < numRectangle && rectangle[y] == null) {

				y++;

			}

			// quando esco dal while ho trovato l'indice di un rettangolo valido

			// oppure sono finiti i rettangoli

			// ricalcolo il rettangolo minimo iniziale

			rectMin = rectMin.union(rectangle[y]);

			minSize = rectMin.getSize();

			minArea = minSize.getHeight() * minSize.getWidth();

			min = y;

		}

		// ora devo risistemare il nodo corrente,

		// togliendo i buchi che si sono formati nell'array dei rettangoli

		for (int j = 0; j < (numRectangle / 2) + 1; j++) {

			if (rectangle[j] == null) {

				for (int h = j + 1; h < numRectangle; h++) {

					if (rectangle[h] != null) {

						// sposto un rettangolo valido in h

						// nel buco trovato in j

						rectangle[j] = rectangle[h];

						rectangle[h] = null;

						childNode[j] = childNode[h];

						if (childNode[j] != null)
							childNode[j].setIndex(j);

						childNode[h] = null;

						// esco dal ciclo

						h = numRectangle + 1;

					}

				}

			}

		}

		numRectangle = (numRectangle / 2) + 1;

		return addedNode;

	}

	public void FindLeaf(Rectangle now_rectangle, Integer integer, ArrayList list) {

		if (childNode[0] == null) {

			for (int i = 0; i < numRectangle; i++) {

				if (now_rectangle.equals(rectangle[i])) {

					// riempio la lista delle foglie che contengono il rettangolo r

					list.add(this);

					list.add(integer);

				}

			}

		} else {

			for (int i = 0; i < numRectangle; i++) {

				if (rectangle[i].contains(now_rectangle)) {

					// scendo di livello

					if (childNode[i] != null)
						childNode[i].FindLeaf(now_rectangle, new Integer(integer.intValue() + 1), list);

				}

			}

		}

	}

	// restituisce il numero di rettangoli del nodo

	public int numRect() {

		return this.numRectangle;

	}

	// restituisce il rettangolo i-esimo

	public Rectangle getRect(int i) {

		return rectangle[i];

	}

	// restituisce il nodo figlio i-esimo

	public Node getChild(int i) {

		return childNode[i];

	}

}
