package core;

import models.Message;

import java.util.*;
import java.util.stream.Collectors;

public class DiscordImpl implements Discord {
    private Map<String, Message> messagesById;
    private Map<String, Set<Message>> messagesByChannel;
    

    public DiscordImpl() {
        this.messagesById = new LinkedHashMap<>();
        this.messagesByChannel = new HashMap<>();
    }

    @Override
    public void sendMessage(Message message) {
        this.messagesById.put(message.getId(), message);

        if (!this.messagesByChannel.containsKey(message.getChannel())) {
            this.messagesByChannel.put(message.getChannel(), new LinkedHashSet<>());
        }

//        Set<Message> messages = this.messagesByChannel.get(message.getChannel());
//        messages.add(message);
//        this.messagesByChannel.put(message.getChannel(), messages);

        this.messagesByChannel.get(message.getChannel()).add(message);
    }

    @Override
    public boolean contains(Message message) {
        return this.messagesById.containsKey(message.getId());
    }

    @Override
    public int size() {
        return this.messagesById.size();
    }

    @Override
    public Message getMessage(String messageId) {
        Message message = this.messagesById.get(messageId);
        if (message == null) {
            throw new IllegalArgumentException();
        }
        return message;
    }

    @Override
    public void deleteMessage(String messageId) {
        Message remove = this.messagesById.remove(messageId);
        if (remove == null) {
            throw new IllegalArgumentException();
        }

        this.messagesByChannel.get(remove.getChannel()).remove(remove);
    }

    @Override
    public void reactToMessage(String messageId, String reaction) {
        Message message = this.getMessage(messageId);

        message.getReactions().add(reaction);
    }

    @Override
    public Iterable<Message> getChannelMessages(String channel) {
        Set<Message> messages = this.messagesByChannel.get(channel);
        if (messages == null) {
            throw new IllegalArgumentException();
        }

        return messages;
    }

    @Override
    public Iterable<Message> getMessagesByReactions(List<String> reactions) {
        return this.messagesById.values()
                .stream()
                .filter(m -> new HashSet<>(m.getReactions()).containsAll(reactions))
                .sorted((l, r) -> {
                    if (l.getReactions().size() != r.getReactions().size()) {
                        return r.getReactions().size() - l.getReactions().size();
                    }

                    return l.getTimestamp() - r.getTimestamp();

                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getMessageInTimeRange(Integer lowerBound, Integer upperBound) {
        return this.messagesById.values()
                .stream()
                .filter(m -> m.getTimestamp() >= lowerBound && m.getTimestamp() <= upperBound)
                .sorted((l,r)->{
                    int lChannelMessagesCount = this.messagesByChannel.get(l.getChannel()).size();
                    int rChannelMessagesCount = this.messagesByChannel.get(r.getChannel()).size();

                    return rChannelMessagesCount - lChannelMessagesCount;
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getTop3MostReactedMessages() {
        return this.messagesById.values()
                .stream()
                .sorted((l,r)-> r.getReactions().size() - l.getReactions().size()
                ).limit(3).collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getAllMessagesOrderedByCountOfReactionsThenByTimestampThenByLengthOfContent() {
        return this.messagesById.values()
                .stream()
                .sorted((l,r)->{
                    int lReactionSize = l.getReactions().size();
                    int rReactionSize = r.getReactions().size();

                    if (lReactionSize != rReactionSize) {
                        return rReactionSize - lReactionSize;
                    }

                    if (l.getTimestamp() != r.getTimestamp()) {
                        return l.getTimestamp() - r.getTimestamp();
                    }

                    return l.getContent().length() - r.getContent().length();
                }).collect(Collectors.toList());
    }
}
