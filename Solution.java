
/*Queue Reconstruction by Height

Input:
[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Output:
[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]





Each element[x,y] = [height, beforePeople]






process in decresing height 7,6,5,4  and reorder the elements

Stack 
Element index destribution---------------------------------
7 -> 0,1
6 ->1
5 ->0,2
4 -> 4

Process---------------------------------
7,0

stack1: _
        7,0 (add element)

7,1

stack1: 7,0
		7,0 -> 7,1 (add element)

6,1

Stack1 : 7,0 -> 7,1 
		 7,0 (pop elements till (1) left from stack1 into stack2)
		 7,0 -> 6,1 (add current element under process)
		 7,0 -> 6,1 -> 7,1 (put back all element from stack2)

5,0

Stack1: 7,0 -> 6,1 -> 7,1
        -  (pop elements till(0) left from stack1 into stack2)
        5,0 (add current element under process)
        5,0 -> 7,0 -> 6,1 -> 7,1 (put back all element from stack2)

5,2

Stack1: 5,0 -> 7,0 -> 6,1 -> 7,1
		5,0 -> 7,0 (pop elements till(2) left from stack1 into stack2)
		5,0 -> 7,0 -> 5,2 (add current element under process)
		5,0 -> 7,0 -> 5,2 -> 6,1 -> 7,1 (put back all element from stack2)

4,4

Stack1: 5,0 -> 7,0 -> 5,2 -> 6,1 -> 7,1
        5,0 -> 7,0 -> 5,2 -> 6,1 (pop elements till(4) left from stack1 into stack2)
        5,0 -> 7,0 -> 5,2 -> 6,1 -> 4,4 (add current element under process)
		5,0 -> 7,0 -> 5,2 -> 6,1 -> 4,4 -> 7,1 (put back all element from stack2)

*/
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        
    	// generating map of height against positions
        Map<Integer,Set<Integer>> heightPosMap = new HashMap<>();
        for (int i=0; i< people.length; i++) {
            if (heightPosMap.containsKey(people[i][0])) {
                heightPosMap.get(people[i][0]).add(people[i][1]);
            } else {
                Set<Integer> pos = new TreeSet<>();
                pos.add(people[i][1]);
                heightPosMap.put(people[i][0],pos);
            }
        }

        //Get Elements in decreasing order 7,6,5,4
        final List<Integer> doheights = new ArrayList<>(heightPosMap.keySet());
        Collections.reverse(doheights);
        
        //initialising stack processing 
        //Element class declaration for simple processing
        final Stack<Element> sMain = new Stack<>();
        final Stack<Element> sHelp = new Stack<>();
        
        
        //Processing our algo
        for(int height: doheights) {
            for (int beforePeople: heightPosMap.get(height)) {
            	
            	// stack and before people matches
                if (sMain.size() == beforePeople) {
                	
                	// add current element
                    sMain.push(new Element(beforePeople, height));
                } else {
                	
                	//Remove element till size matches beforepeople
                    while (sMain.size() > beforePeople) {
                        sHelp.push(sMain.pop());
                    }
                    
                    //add current element
                    sMain.push(new Element(beforePeople, height));

                    //add back poped element
                    while(!sHelp.isEmpty()) {
                        sMain.push(sHelp.pop());
                    }
                }
            }
            
        }
        
        //Contruct the result from stack
        int count = people.length -1;
        while(!sMain.isEmpty()) {
            Element element = sMain.pop();
            people[count][0] = element.getHeight();
            people[count][1] = element.getbeforePeople();
            count--;
        }
        return people;
    }

    class Element {
        int beforePeople;
        int height;
        
        public Element(int beforePeople, int height) {
            this.beforePeople = beforePeople;
            this.height = height;
        }
        
        int getbeforePeople() {
            return this.beforePeople;
        }
        
        int getHeight() {
            return this.height;
        }
    }
}
