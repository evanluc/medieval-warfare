package newworldorder.common.network.command;

public interface ReplyCommandHandler {
	public Object handleAndReply(AbstractCommand command);
}
