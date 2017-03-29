public class Stack
{
	public String data[];
	private int top;

	public Stack()
	{
		this.data = new String[1]; //default length 1
		this.top = 0;
	}

	public Stack(int length)
	{
		this.data = new String[length];
		this.top = 0;
	}

	public void push(String text)
	{
		if (this.top == this.data.length - 1) {
			String temp[] = new String[2 * this.data.length];
			for(int i = 0; i < this.data.length; i++) {
				temp[i] = this.data[i];
			}
			this.data = temp;
		}
		this.top++;
		data[top] = text;
	}

	public String pop()
	{
		if (this.top == -1) {
			return null;
		}
		String popped = data[this.top];
		data[this.top] = null;
		this.top--;
		return popped;
	}

	public String peek()
	{
		return data[top];
	}

	public int getSize()
	{
		return this.top;
	}
}