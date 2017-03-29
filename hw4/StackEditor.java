import java.util.Scanner;

public class StackEditor
{
	private Stack textStack;
	private Scanner scanner;
	private boolean running;
	private int numLines;

	public StackEditor()
	{
		textStack = new Stack();
		scanner = new Scanner(System.in);
		running = false;
		numLines = 0;
	}

	public static void main(String[] args) {
		StackEditor editor = new StackEditor();	//Create new StackEditor object.
		editor.readScan();						//Read the Command Line using the Scanner.
	}

	private void readScan()
	{
		this.running = true;					//Start running.

		while (this.running) {
			//Read the next line using the Scanner.
			String line = this.scanner.nextLine();
			this.numLines = this.textStack.getSize();
			if (line.startsWith("#")) {		//If it starts with #,
				this.readCommand(line);		//interpret as a command.
			}
			else {
				this.addLine(line);			//Otherwise, add as a new line.
			}
		}
	}

	private void readCommand(String command)
	{
		if (isCommand(command)) {
			switch (command) {
				case "#reverse":
					reverseStack();
					break;
				case "#print":
					printStack();
					break;
				case "#printReverse":
					printReverse();
					break;
				case "#delete":
					deleteStack();
					break;
				case "#exit":
					quitScan();
					break;
			}
			if (command.startsWith("#print ")) {						//If command starts with #print ,
				printLine(Integer.parseInt(command.substring(7)));		//Find the line to print and print it.
			}
			else if (command.startsWith("#delete ")) {					//If the command starts with #delete ,
				deleteLine(Integer.parseInt(command.substring(8)));		//Find the line to delete and delete it.
			}
		}
		else {
			//Error if command is not recognized.
			System.out.println("Error: Command not recognized. Commands are: reverse, print, printReverse, delete, and exit.");
		}
	}

	private boolean isCommand(String text)
	{
		//If the text is a command, return true.
		if (text.equals("#reverse") || text.equals("#print") || text.equals("#printReverse") || text.startsWith("#print ") ||
			text.equals("#delete") || text.startsWith("#delete ") || text.equals("#exit")) {
			return true;
		}
		return false;
	}

	protected void addLine(String text)
	{
		textStack.push(text);	//Push text onto stack.
	}

	protected void reverseStack()
	{
		Stack temp = new Stack(numLines);

		//Push into temp stack what we pop from textStack.
		for (int i = 0; i < numLines; i++) {
			temp.push(textStack.pop());
		}
		//Set our textStack to be equal to this reversed temp Stack.
		textStack = temp;
	}

	protected void printStack()
	{
		Stack temp = new Stack(numLines);

		//Push into temp stack what we pop from textStack.
		for (int i = 0; i < numLines; i++) {
			temp.push(textStack.pop());
		}
		//Pop each item from temporary stack, printing as we go,
		//pushing them back into original stack.
		for (int j = 0; j < numLines; j++) {
			String pushed = temp.pop();
			System.out.println(pushed);
			textStack.push(pushed);
		}
	}

	protected void printReverse()
	{
		Stack temp = new Stack(numLines);

		//Pop out all lines of textStack, printing as we go,
		//and pushing into a temporary Stack
		for (int i = 0; i < numLines; i++) {
			String popped = textStack.pop();
			System.out.println(popped);
			temp.push(popped);
		}
		//Reset original Stack.
		for (int j = 0; j < numLines; j++) {
			textStack.push(temp.pop());
		}
	}

	protected void printLine(int lineNum)
	{
		//If this line exists in the stack, print it.
		if (numLines >= lineNum && lineNum > 0) {
			int numLinesToPop = numLines - lineNum;
			Stack temp = new Stack(numLinesToPop);
			//loop until we reach line to be printed
			for (int i = 0; i < numLinesToPop; i++) {
				temp.push(textStack.pop());
			}

			System.out.println(textStack.peek());		//Print the upcoming line

			//Reset original Stack.
			for (int j = 0; j < numLinesToPop; j++) {
				textStack.push(temp.pop());
			}
		}
		else {
			System.out.println("Invalid line number.");
		}
	}

	protected void deleteStack()
	{
		//Loops through the stack, popping everything out.
		for (int i = 0; i < numLines; i++) {
			textStack.pop();
		}
	}

	protected void deleteLine(int lineNum)
	{
		//if this line exists in the stack, delete it.
		if (numLines >= lineNum && lineNum > 0) {
			int numLinesToPop = numLines - lineNum;
			Stack temp = new Stack(numLinesToPop);
			//pop everything off and push onto temporary stack,
			//until we reach line to be deleted
			for (int i = 0; i < numLinesToPop; i++) {
				temp.push(textStack.pop());
			}
			//pop off line to be deleted 
			textStack.pop();
			//pop all the lines off the temporary stack,
			//and push back onto original stack
			for (int j = 0; j < numLinesToPop; j++) {
				textStack.push(temp.pop());
			}
		}
		else {
			System.out.println("Invalid line number.");
		}
	}

	private void quitScan()
	{
		//No longer running.
		running = false;
	}
}