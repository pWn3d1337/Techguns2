package techguns.client.particle.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ParticleList<E> implements Iterable<E>{
	protected ParticleListElement<E> first;	
	protected ParticleListElement<E> last;	
	int size=0;
	
	public ParticleList() {
		super();
		first=null;
		last=null;
	}

	public void add(E e) {
		if(first==null) {
			first = new ParticleListElement<E>(e);
			last=first;
		} else {
			ParticleListElement<E> n = new ParticleListElement<E>(e);
			last.next=n;
			n.prev=last;
			last=n;
		}
		size++;
	}
	
	public void debugPrintList() {
		int i=0;
		ParticleListElement<E> elem = this.first;
		if (elem==null) {
			System.out.println("|EMPTY|");
			return;
		}
		while(elem.next!=null) {
			System.out.println("|"+i+":"+elem.e+"|");
			elem=elem.next;
			i++;
		}
		System.out.println("|"+i+":"+elem.e);
		System.out.println("<SIZE>:"+this.size);
		//System.out.println("<FIRST>:"+this.first.e.toString());
		//System.out.println("<LAST>:"+this.last.e.toString());
	}
	
	public void doBubbleSort(int times, Comparator<E> comp) {
		if(this.size>0) {
			boolean changes=true;
			for(int i=0;i<times && changes;++i) {
				changes=false;
				
				ParticleListIterator<E> it = this.iterator();
				E prev=null;
				E current=null;
	
				while(it.hasNext()) {
					current = it.next();
					if(prev!=null) {
						if(comp.compare(prev, current)>0) {
							it.swapWithPrev();
							changes=true;
						}
					}
					prev=current;
				}
				
			}
		}
	}
	
	
	public void remove(ParticleListElement<E> listElem) {
		if(listElem==first) {
			first = listElem.next;
			if (first!=null) {
				first.prev=null;
			}
		} else if(listElem==last) {
			last=listElem.prev;
			if(last!=null) {
				last.next=null;
			}
		} else {
			
			listElem.prev.next=listElem.next;
			
			if(listElem.next!=null) { //This should not happen, but there where crash reports
				listElem.next.prev=listElem.prev;
			}
			//listElem.next=null;
			listElem.prev=null;
		}
		size--;
	}
	
	/*public void addSorted(E e, Comparator<E> comp) {
		if(first==null) {
			first = new ParticleListElement<E>(e);
			last=first;
		} else {
			ParticleListElement<E> current = first;
			if(last==first) {
				//only one elem
				if (comp.compare(e, first.e)<0) {
					//add first
					first = new ParticleListElement<E>(e);
					first.next=last;
					last.prev=first;
					
				} else {
					//add last;
					add(e);
				}
			}
			boolean found=false;
			while(current!=last && !found) {
	
				if(comp.compare(e, current.e)<0) {
					
					ParticleListElement<E> n = new ParticleListElement<E>(e);
					n.next=current.next;
					current.next=n;
					n.prev=current;
					found=true;
				}
			}
			
			if(!found) {
				//Add last
				add(e);
			}

		}
	}*/
	
	protected static class ParticleListElement<E> {
		protected ParticleListElement<E> next;
		protected ParticleListElement<E> prev;
		protected E e;
		
		public ParticleListElement(E e) {
			super();
			this.e = e;
		}

	}

	/**
	 * Get number of particles, INEFFICIENT
	 * @return
	 */
	public int getSizeDebug() {
		if(first==null) {
			return 0;
		}
		ParticleListElement<E> elem = first;
		int i =1;
		while(elem!=last) {
			elem=elem.next;
			i++;
		}
		return i;
	}
	
	public int getSize() {
		return size;
	}

	@Override
	public ParticleListIterator<E> iterator() {
		return new ParticleListIterator<E>(this);
	}

	public static class ParticleListIterator<E> implements Iterator<E> {

		public ParticleListIterator(ParticleList<E> list) {
			super();
			this.list=list;
			this.current=null;
		}

		ParticleList<E> list;
		ParticleListElement<E> current;
		
		@Override
		public boolean hasNext() {
			if(current==null && list.first==null) {
				return false;
			}
			if(current!=null && current.next==null) {
				return false;
			}
			return current != list.last;
		}

		@Override
		public E next() {
			if( !hasNext()) {
				throw new NoSuchElementException("ParticleList has no more elements!");
			}
			if(current==null) {
				current = list.first;
			} else {
				current = current.next;
			}
			return current.e;
		}
		
		public void swapWithPrev() {
			if(current!=null && current!=list.first) {
				ParticleListElement<E> prev = current.prev;
				
				if(prev.prev!=null) {
					prev.prev.next=current;
				} else {
					list.first=current;
				}
				
				if(current.next!=null) {
					current.next.prev=prev;
				}
				
				if(list.last==current) {
					list.last=prev;
				}
				
				current.prev=prev.prev;
				prev.next=current.next;
				
				current.next=prev;
				prev.prev=current;		
				
				//set iterator the prev(=new current)
				current=prev;
			}
		}
		
		@Override
		public void remove() {
			if(current!=null) {
			/*	ParticleListElement<E> next=null;
				if(hasNext()) {
					next = current.next;
				}*/
				
				list.remove(current);
				//current=next;
			} else {
				throw new IllegalStateException("Current element is null!");
			}
		}
	}
	
}
