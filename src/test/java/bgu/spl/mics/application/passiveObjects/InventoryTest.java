package bgu.spl.mics.application.passiveObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.print.Book;
import java.util.Vector;

import static org.junit.Assert.*;

public class InventoryTest {

    Inventory testInv;
    BookInventoryInfo Stormlight = new BookInventoryInfo("way of kings", 1 , 100);
    BookInventoryInfo Mistborn = new BookInventoryInfo("Last Empire", 0 , 50);
    BookInventoryInfo[] Sanderson = {Stormlight,Mistborn};
    Vector<BookInventoryInfo> Empty = new Vector<BookInventoryInfo>(0);;


    /*
    build new Inventory - testInv.
     */
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /*
    use the get instance method which calls the cunstructor if the PARAM ins't initiate.
    this function cant return a NULL.
     */
    @Test
    public void getInstance() {
        assertNull(testInv);
        testInv = Inventory.getInstance();
        assertNotNull (testInv);
    }

    @Test
    /*
    should add array of books to the inventory , we assume that the book we are adding isn't already in the store (forum)
    we should check that the Book Inventory Info is changed with the books added by this func'
     */
    public void load() {
        assertEquals("way of kings" ,Sanderson[0].getBookTitle() );
        assertEquals(0 , Sanderson[1].getAmountInInventory());
        assertEquals(1 , Sanderson[0].getAmountInInventory());
    }

    @Test
    /*
    should try and take a book out of the store into delivery. if there is no more copy of that book should get the enum NOT_IN_STOCK
    otherwise should get the enum SUCCESSFULLY_TAKEN.
    the amount of said book should go down by one, if its reach 0 - the book should be marked out of stock.
     */
    public void take() {
        testInv=Inventory.getInstance();
        testInv.load(Sanderson);
        assertEquals(OrderResult.NOT_IN_STOCK,testInv.take("Last Empire") );
        assertEquals(OrderResult.SUCCESSFULLY_TAKEN, testInv.take("way of kings"));
        testInv.take("Stromlight");
        assertEquals(OrderResult.NOT_IN_STOCK,testInv.take("way of kings") );
        assertEquals(OrderResult.NOT_IN_STOCK,testInv.take("Altanaris") );
    }

    @Test
    /*
    should check the price of a book, if it is available in stock it should return its price (check AssertEqual (-1 / price , the price of the book)
    return int: -1 if the book is unavailable , or the price of the book otherwise
    we should create a book with an amount (0 / more) and price- and check its availabilty.
     */
    public void checkAvailabiltyAndGetPrice() {
        testInv=Inventory.getInstance();
        testInv.load(Sanderson);
        assertEquals(-1 ,testInv.checkAvailabiltyAndGetPrice("Last Empire"));
        assertEquals(100 ,testInv.checkAvailabiltyAndGetPrice("way of kings"));
        assertEquals(-1 ,testInv.checkAvailabiltyAndGetPrice("Warbreaker"));
    }

    @Test
    /*
    should print a hash map into a file. the hash map should be SERIALIZED by the the book title
    should check that the file is serialed. check if the key if write (book name) and the value is the amount in the inventory.

     */
    public void printInventoryToFile() {
    }
}