package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Request;
import seedu.address.model.order.exceptions.DuplicateRequestException;
import seedu.address.testutil.RequestBuilder;

public class RequestBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OrderBook orderBook = new OrderBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), orderBook.getOrderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        orderBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyOrderBook_replacesData() {
        OrderBook newData = getTypicalOrderBook();
        orderBook.resetData(newData);
        assertEquals(newData, orderBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Request editedAlice = new RequestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER)
                .build();
        List<Request> newRequests = Arrays.asList(ALICE, editedAlice);
        OrderBookStub newData = new OrderBookStub(newRequests);

        thrown.expect(DuplicateRequestException.class);
        orderBook.resetData(newData);
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        orderBook.hasOrder(null);
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(orderBook.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        orderBook.addOrder(ALICE);
        assertTrue(orderBook.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInOrderBook_returnsTrue() {
        orderBook.addOrder(ALICE);
        Request editedAlice = new RequestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER)
                .build();
        assertTrue(orderBook.hasOrder(editedAlice));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        orderBook.getOrderList().remove(0);
    }

    /**
     * A stub ReadOnlyOrderBook whose requests list can violate interface constraints.
     */
    private static class OrderBookStub implements ReadOnlyOrderBook {
        private final ObservableList<Request> requests = FXCollections.observableArrayList();

        OrderBookStub(Collection<Request> request) {
            this.requests.setAll(request);
        }

        @Override
        public ObservableList<Request> getOrderList() {
            return requests;
        }
    }

}
