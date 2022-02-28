public class HanoiMain {
    public static void main(String[] args) {
        Hanoi hanoi1 = new Hanoi();
        Hanoi hanoi2 = new Hanoi(3);
        Hanoi hanoi3 = new Hanoi(9);
        
        hanoitest(hanoi1);
        System.out.println();
        hanoitest(hanoi2);
        System.out.println();
        hanoitest(hanoi3);
    }
    
    static void hanoitest (Hanoi hanoi) {
        System.out.println(hanoi.toString());
       
        System.out.println("L-M Is move legal?: " + 
                            hanoi.isLegalMove(Rod.LEFT, Rod.MIDDLE));
        hanoi.makeMove(Rod.LEFT, Rod.MIDDLE);
        System.out.println(hanoi.toString());//1
        
        System.out.println("L-M Is move legal?: " + 
                            hanoi.isLegalMove(Rod.LEFT, Rod.MIDDLE));
        hanoi.makeMove(Rod.LEFT, Rod.MIDDLE);
        System.out.println(hanoi.toString());
        
        System.out.println("Is solved?: " + 
                            hanoi.isSolved());
        
        hanoi.makeMove(Rod.LEFT, Rod.RIGHT);
        System.out.println(hanoi.toString());//2
        
        hanoi.makeMove(Rod.MIDDLE, Rod.RIGHT);
        System.out.println(hanoi.toString());//3
        
        hanoi.makeMove(Rod.LEFT, Rod.MIDDLE);
        System.out.println(hanoi.toString());//4
        
        hanoi.makeMove(Rod.RIGHT, Rod.LEFT);
        System.out.println(hanoi.toString());//5
        
        hanoi.makeMove(Rod.RIGHT, Rod.MIDDLE);
        System.out.println(hanoi.toString());//6
        
        hanoi.makeMove(Rod.LEFT, Rod.MIDDLE);
        System.out.println(hanoi.toString());//7
        
        hanoi.makeMove(Rod.LEFT, Rod.RIGHT);
        System.out.println(hanoi.toString());//8
        
        hanoi.makeMove(Rod.MIDDLE, Rod.RIGHT);
        System.out.println(hanoi.toString());//9
        
        hanoi.makeMove(Rod.MIDDLE, Rod.LEFT);
        System.out.println(hanoi.toString());//10
        
        hanoi.makeMove(Rod.RIGHT, Rod.LEFT);
        System.out.println(hanoi.toString());//11
        
        hanoi.makeMove(Rod.MIDDLE, Rod.RIGHT);
        System.out.println(hanoi.toString());//12
        
        hanoi.makeMove(Rod.LEFT, Rod.MIDDLE);
        System.out.println(hanoi.toString());//13
        
        hanoi.makeMove(Rod.LEFT, Rod.RIGHT);
        System.out.println(hanoi.toString());//14
        
        hanoi.makeMove(Rod.MIDDLE, Rod.RIGHT);
        System.out.println(hanoi.toString());//15
        
        hanoi.makeMove(Rod.MIDDLE, Rod.RIGHT);
        System.out.println(hanoi.toString());
        
        System.out.println("Is solved?: " + 
                            hanoi.isSolved());
        
        System.out.println("NoOfDisks: " + 
                            hanoi.getNoOfDisks());
        System.out.println("NoOfMoves: " + 
                            hanoi.getNoOfMoves());
    }
}