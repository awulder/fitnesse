package fitnesse.wikitext.parser;

import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.Clock;
import util.DateAlteringClock;

public class TodayTest {

    @Before
    public void setUp() {
        new DateAlteringClock(new GregorianCalendar(2002, 2, 4, 15, 6, 7).getTime()).freeze();
    }

    @After
    public void tearDown() {
        Clock.restoreDefaultClock();
    }

    @Test
    public void translatesTodays() {
        ParserTestHelper.assertTranslatesTo("!today", "04 Mar, 2002");
        ParserTestHelper.assertTranslatesTo("!today -t", "04 Mar, 2002 15:06");
        ParserTestHelper.assertTranslatesTo("!today -xml", "2002-03-04T15:06:07");
        ParserTestHelper.assertTranslatesTo("!today (MMM)", "Mar");
        ParserTestHelper.assertTranslatesTo("!today (dd MMM)", "04 Mar");
        ParserTestHelper.assertTranslatesTo("!today (dd MMM", "!today (dd MMM");
    }

    @Test
    public void translatesWithDayIncrements() {
        ParserTestHelper.assertTranslatesTo("!today +5", "09 Mar, 2002");
        ParserTestHelper.assertTranslatesTo("!today +10", "14 Mar, 2002");
        ParserTestHelper.assertTranslatesTo("!today -5", "27 Feb, 2002");
        ParserTestHelper.assertTranslatesTo("!today -5.", "27 Feb, 2002.");
    }

    @Test
    public void translatesWithDayIncrementsAndCustomFormat() {
        ParserTestHelper.assertTranslatesTo("!today (ddMMM) +5", "09Mar");
    }

    @Test
    public void translatesInTable() {
        ParserTestHelper.assertTranslatesTo("|!today (ddMMM)|\n", ParserTestHelper.tableWithCell("04Mar"));
        ParserTestHelper.assertTranslatesTo("|!today -t|\n", ParserTestHelper.tableWithCell("04 Mar, 2002 15:06"));
    }
}
