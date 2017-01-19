package my_first_plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler implements IConsoleListener, IPatternMatchListener {
	/**
	 * The constructor.
	 */
	
	private String pattern;
	private int matchCount = 0;
	
	public SampleHandler() {
		pattern = "\\d+";
		matchCount = 0;
		ConsolePlugin.getDefault().getConsoleManager().addConsoleListener(this);
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Matches",
				this.matchCount + " matches found");
		return null;
	}

	@Override
	public void consolesAdded(IConsole[] consoles) {
		for(IConsole console : consoles) {
			if(console instanceof TextConsole) {
				((TextConsole) console).addPatternMatchListener(this);
			}
		}
	}

	@Override
	public void consolesRemoved(IConsole[] consoles) {
		for(IConsole console : consoles) {
			if(console instanceof TextConsole) {
				((TextConsole) console).removePatternMatchListener(this);
			}
		}
	}

	@Override
	public void connect(TextConsole arg0) {
		
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public void matchFound(PatternMatchEvent arg0) {
		this.matchCount++;
	}

	@Override
	public int getCompilerFlags() {
		return 0;
	}

	@Override
	public String getLineQualifier() {
		return null;
	}

	@Override
	public String getPattern() {
		return pattern;
	}
}
