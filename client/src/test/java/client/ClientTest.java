package client;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientTest {
    Client c = new Client();

    @Test
    void addressIP(){
        String add =  c.getAddress();
        Assert.assertEquals(add,c.getAddress());
    }

    @Test
    void hostName(){
        String name = c.getName();
        Assert.assertEquals(name,c.getName());
    }
    /* A tester */

    public ClientTest() throws UnknownHostException {

    }

}