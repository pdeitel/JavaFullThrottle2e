// Fig. 7.19: RegexExamples.java
// Matching entire Strings to regular expressions.
// Cut down from book example
public class RegexExamples {
   public static void main(String[] args) {
      // fully match five digits
      System.out.println("Matching against: \\d{5}");
      System.out.printf("02215: %b; 9876: %b%n%n",
         "02215".matches("\\d{5}"), "9876".matches("\\d{5}"));
   }
}

/*
 **************************************************************************
 * (C) Copyright 1992-2025 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************
*/

