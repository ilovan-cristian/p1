package copilot12354.mpp.services;

import copilot12354.mpp.Employee;
import copilot12354.mpp.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = {"Authorization","Content-Type"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
)
@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user)
    {
        String id = user.get("username");
        String password = user.get("password");

        System.out.println("Id: " + id);
        System.out.println("Password: " + password);

        Optional<Employee> optionalEmployee = employeeRepo.findOne(Integer.parseInt(id));

        if (optionalEmployee.isPresent())
        {
            System.out.println("ID OK");
            Employee employee = optionalEmployee.get();
            if (employee.getPassword().equals(password))
            {
                System.out.println("PASSWORD OK");
                String token = jwtUtil.generateToken(id);
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
