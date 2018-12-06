package messagesystem;

public final class MessageSystem {
//    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
//    private static final int DEFAULT_STEP_TIME = 10;
//
//    private final List<Thread> workers;
//    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
//    private final Map<Address, Addressee> addresseeMap;
//
//    public MessageSystem() {
//        workers = new ArrayList<>();
//        messagesMap = new HashMap<>();
//        addresseeMap = new HashMap<>();
//    }
//
//    public Map<Address, LinkedBlockingQueue<Message>> getMessagesMap() {
//        return messagesMap;
//    }
//
//    public void addAddressee(Addressee addressee) {
//        addresseeMap.put(addressee.getSocket(), addressee);
//        messagesMap.put(addressee.getSocket(), new LinkedBlockingQueue<>());
//    }
//
//    public void sendMessage(Message message) {
//        messagesMap.get(message.getTo()).add(message);
//    }
//
//    public void addWorker (Addressee addressee){
//        String name = "MS-worker-" + addressee.getSocket().getId();
//
//        Thread thread = new Thread(() -> {
//            LinkedBlockingQueue<Message> queue = messagesMap.get(addressee.getSocket());
//            while (true) {
//                try {
//                    Message message = queue.take();
//                    System.out.println("queue --" + message.getFrom().toString() + "-" +message.getTo().toString());
//                    message.exec(addressee);
//                } catch (InterruptedException e) {
//                     logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
//                    return;
//                }
//            }
//        });
//            thread.setName(name);
//            thread.start();
//            workers.add(thread);
//    }
//
//
//    public Map<Address, Addressee> getAddresseeMap() {
//        return addresseeMap;
//    }
//
//    public void dispose() {
//        workers.forEach(Thread::interrupt);
//    }
}
