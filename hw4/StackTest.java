public class StackTest
{
	public static void main(String[] args) {
		StackEditor editor = new StackEditor();

		//Add Lines to the Stack
		System.out.println("Adding lines 1 through 10 to Stack...");

		editor.addLine("Line 1");
		editor.addLine("Line 2");
		editor.addLine("Line 3");
		editor.addLine("Line 4");
		editor.addLine("Line 5");
		editor.addLine("Line 6");
		editor.addLine("Line 7");
		editor.addLine("Line 8");
		editor.addLine("Line 9");
		editor.addLine("Line 10");

		//Print the Stack
		System.out.println("Print the Stack, from line 1 to 10.");
		editor.printStack();

		//Print the reverse of the Stack
		System.out.println("Print the Stack in reverse, from line 10 to 1.");
		editor.printReverse();

		//Reverse the Stack.
		System.out.println("Reversing the Stack...");
		editor.reverseStack();

		//Print the Stack again- should have been reversed
		System.out.println("Print the Reversed Stack, from line 10 to 1.");
		editor.printStack();

		//Reverse the Stack back to original order.
		System.out.println("Reversing the Stack again...");
		editor.reverseStack();

		//Print first line of Stack.
		System.out.println("Print the first line of the Stack, line 1.");
		editor.printLine(1);

		//Print last line of Stack.
		System.out.println("Print the last line of the Stack, line 10.");
		editor.printLine(10);

		//Delete first line of Stack, and print it again.
		System.out.println("Delete the first line of the Stack, line 1, and print the full stack again.");
		editor.deleteLine(1);
		editor.printStack();

		//Delete the last line of Stack, and print again.
		System.out.println("Delete the last line of the Stack, line 10, and print the full stack again.");
		editor.deleteLine(9);
		editor.printStack();

		//Delete the whole Stack.
		System.out.println("Deleting the Stack...");
		editor.deleteStack();

		//Print Stack.
		System.out.println("Print the Stack, now empty.");
		editor.printStack();

	}
}