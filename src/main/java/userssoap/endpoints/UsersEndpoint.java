package userssoap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import userssoap.model.*;
import userssoap.model.Error;
import userssoap.orm.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Endpoint
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://userssoap";

    private final UsersRepository usersRepository;

    @Autowired
    public UsersEndpoint(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createNewUserRequest")
    public OperationResponse createNewUser(@RequestPayload CreateNewUserRequest request) {
        if (usersRepository.findById(request.getUser().getLogin()).isPresent()){
            return new OperationResponse(false,
                    new Error(new ArrayList<String>(){{add("User with this login already exists");}}));
        }

        usersRepository.save(request.getUser());
        return new OperationResponse(true);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserRequest")
    public OperationResponse editUser(@RequestPayload EditUserRequest request) {
        User foundedUser = usersRepository.findById(request.getUser().getLogin()).orElse(null);

        if (foundedUser == null){
            return new OperationResponse(false,
                    new Error(new ArrayList<String>(){{add("User with this login not exists");}}));
        }

        foundedUser.setName(request.getUser().getName());
        foundedUser.setPassword(request.getUser().getPassword());
        foundedUser.getRoles().clear();
        foundedUser.getRoles().addAll(request.getUser().getRoles());
        usersRepository.save(foundedUser);
        return new OperationResponse(true);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeUserRequest")
    public OperationResponse removeUser(@RequestPayload RemoveUserRequest request){
        if (!usersRepository.findById(request.getLogin()).isPresent()){
            return new OperationResponse(false,
                    new Error(new ArrayList<String>(){{add("User with this login not exists");}}));
        }

        usersRepository.deleteById(request.getLogin());
        return new OperationResponse(true);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
    public GetUsersResponse getUsers(){
        GetUsersResponse response = new GetUsersResponse();
        List<UserDto> users = StreamSupport.stream(usersRepository.findAll().spliterator(), false)
                .map(UserDto::new).collect(Collectors.toList());
        response.getUsers().addAll(users);
        return response;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    public GetUserResponse getUser(@RequestPayload GetUserRequest request){
        GetUserResponse response = new GetUserResponse();
        response.setUser(usersRepository.findById(request.getLogin()).orElse(null));
        return response;
    }
}
