package userssoap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import userssoap.model.GetUserRequest;
import userssoap.model.GetUserResponse;
import userssoap.orm.UsersRepository;

@Endpoint
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://userssoap";

    private UsersRepository usersRepository;

    @Autowired
    public UsersEndpoint(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    public GetUserResponse getUser(@RequestPayload GetUserRequest request){
        GetUserResponse response = new GetUserResponse();
        response.setUser(usersRepository.findById(request.getLogin()).orElse(null));
        return response;
    }
}
