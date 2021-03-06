package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import seedu.address.model.HealthWorkerBook;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertHealthWorkerListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertRequestListMatching;

//import java.nio.file.Path;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.HealthWorkerListPanelHandle;
import guitests.guihandles.InfoPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.RequestListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;

import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.ClearCommand;
//import seedu.address.logic.commands.FilterHealthWorkerCommand;
//import seedu.address.logic.commands.ListHealthWorkerCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.ui.AutoCompleteTextField;
import seedu.address.ui.InfoPanel;

/**
 * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class AddressBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", AutoCompleteTextField.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        //testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getInfoPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    //protected AddressBook getInitialData() {
    //return TypicalPersons.getTypicalAddressBook();
    //}

    /**
     * Returns the directory of the data file.
     */
    //protected Path getDataFileLocation() {
    //return TestApp.SAVE_LOCATION_FOR_TESTING;
    //}

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public RequestListPanelHandle getRequestListPanel() {
        return mainWindowHandle.getRequestListPanel();
    }

    public InfoPanelHandle getInfoPanel() {
        return mainWindowHandle.getInfoPanel();
    }

    public HealthWorkerListPanelHandle getHealthWorkerListPanel() {
        return mainWindowHandle.getHealthWorkerListPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getInfoPanel());
    }

    /**
     * Displays all persons in the address book.
     */
    //protected void showAllPersons() {
    //  executeCommand(ListHealthWorkerCommand.COMMAND_WORD);
    //assertEquals(getModel().getAddressBook().getPersonList().size(), getModel().getFilteredPersonList().size());
    //}

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    //protected void showPersonsWithName(String keyword) {
    //executeCommand(FilterHealthWorkerCommand.COMMAND_WORD + " " + keyword);
    //  assertTrue(getModel().getFilteredPersonList().size() < getModel().getAddressBook().getPersonList().size());
    //}

    /**
     * Selects the person at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getRequestListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all persons in the address book.
     */
    //protected void deleteAllPersons() {
    //  executeCommand(ClearCommand.COMMAND_WORD);
    //assertEquals(0, getModel().getAddressBook().getPersonList().size());
    //}

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        //assertEquals(new HealthWorkerBook(expectedModel.getHealthWorkerBook()), testApp.readStorageAddressBook());
        assertRequestListMatching(getRequestListPanel(), expectedModel.getFilteredRequestList());
        assertHealthWorkerListMatching(getHealthWorkerListPanel(), expectedModel.getFilteredHealthWorkerList());
    }

    /**
     * Calls {@code RequestListPanelHandle}, {@code InfoPanel}, {@code HealthWorkerListPanelHandle} and
     * {@code StatusBarFooterHandle} to remember their current states.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getInfoPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getRequestListPanel().rememberSelectedRequestCard();
        getHealthWorkerListPanel().rememberSelectedHealthWorkerCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and there is no new load event.
     * @see InfoPanelHandle#isLoaded()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(!getInfoPanel().isLoaded());
        assertFalse(getRequestListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the info panel's content is updated to display the details of a request from the request list panelt
     * at {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see InfoPanelHandle#isLoaded()
     * @see RequestListPanelHandle#isSelectedRequestCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getRequestListPanel().navigateToCard(getRequestListPanel().getSelectedCardIndex());
        assertTrue(getInfoPanel().isLoaded());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getRequestListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the info panel's url and the selected card in the person list panel remain unchanged.
     * @see InfoPanelHandle#isUrlChanged()
     * @see RequestListPanelHandle#isSelectedRequestCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getInfoPanel().isUrlChanged());
        assertFalse(getRequestListPanel().isSelectedRequestCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM YYYY, hh:mm a");
        String timestamp = formatter.format(new Date(clockRule.getInjectedClock().millis()));
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertRequestListMatching(getRequestListPanel(), getModel().getFilteredRequestList());
        assertHealthWorkerListMatching(getHealthWorkerListPanel(), getModel().getFilteredHealthWorkerList());
        assertEquals(InfoPanel.DEFAULT_PAGE, getInfoPanel().getLoadedUrl());
        //assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
        //      getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
