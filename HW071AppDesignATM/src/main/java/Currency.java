public final class Currency {
    public enum Rubles {
        ONE_HUNDRED_RUB(100),
        TWO_HUNDRED_RUB(200),
        FIVE_HUNDRED_RUB(500),
        ONE_THOUSAND_RUB(1000),
        TWO_THOUSAND_RUB(2000),
        FIVE_THOUSAND_RUB(5000);

        public static final String NAME="RUB";
        private final int value;

        Rubles(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.name().replace("_", " ");
        }
    }

    public enum Dollars {
        ONE_DOLLAR(1),
        FIVE_DOLLARS(5),
        TEN_DOLLARS(10),
        TWENTY_DOLLARS(20),
        FIFTY_DOLLARS(50),
        ONE_HUNDRED_DOLLARS(100);

        public static final String NAME="USD";
        private final int value;

        Dollars(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.name().replace("_", " ");
        }
    }
}
