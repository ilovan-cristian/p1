package copilot12354.mpp.services;

import copilot12354.mpp.Flight;
import copilot12354.mpp.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = {"Authorization","Content-Type"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
)
@RestController
@RequestMapping("/flights")
public class Controller
{
    @Autowired
    private FlightRepo flightRepo;

    @RequestMapping(method = RequestMethod.GET)
    public List<Flight> getAll()
    {
        System.out.println("Get all flights ...");
        return (List<Flight>) flightRepo.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id)
    {
        System.out.println("Get flight by id " + id);
        if (id == null)
            return ResponseEntity.status(400).body("Flight id is null");
        if (id.isEmpty())
            return ResponseEntity.status(400).body("Flight id is empty");
        try
        {
            Integer flightId = Integer.parseInt(id);
            if (flightId < 0)
                return ResponseEntity.status(400).body("Flight id is negative");
            Optional<Flight> flightOpt = flightRepo.findOne(flightId);
            if (flightOpt.isPresent())
            {
                Flight flight = flightOpt.get();
                return new ResponseEntity<Flight>(flight, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>("Flight not found", HttpStatus.NOT_FOUND);
            }
        }
        catch (NumberFormatException e)
        {
            return ResponseEntity.status(400).body("Error parseing flight id");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody Flight flight)
    {
        System.out.println("Saving flight ...");
        if (flight == null)
            return ResponseEntity.status(400).body("flight error");
        flightRepo.save(flight);
        return new ResponseEntity<Flight>(flight, HttpStatus.valueOf(200));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Flight flight, @PathVariable String id)
    {
        System.out.println("Updating flight ...");
        if (flight == null)
            return ResponseEntity.status(400).body("flight error");
        if (id == null)
            return ResponseEntity.status(400).body("id error");
        if (id.isEmpty())
            return ResponseEntity.status(400).body("id empty");
        if (flight.getId() != Integer.parseInt(id))
            return ResponseEntity.status(400).body("flight.getId() != Integer.parseInt(id)");
        try
        {
            Integer flightId = Integer.parseInt(id);
            if (flightId < 0)
                return ResponseEntity.status(400).body("flightId < 0");
            flight.setId(flightId);
            flightRepo.update(flight);
            return new ResponseEntity<Flight>(flight, HttpStatus.valueOf(200));
        }
        catch (NumberFormatException e)
        {
            return ResponseEntity.status(400).body("id integer error");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        System.out.println("Deleting flight ... " + id);
        if (id == null)
            return ResponseEntity.status(400).body("Flight id is null");
        if (id.isEmpty())
            return ResponseEntity.status(400).body("Flight id is empty");
        try
        {
            Integer flightId = Integer.parseInt(id);
            if (flightId < 0)
                return ResponseEntity.status(400).body("Flight id is negative");
            Optional<Flight> flightOpt = flightRepo.findOne(flightId);
            if (flightOpt.isPresent())
            {
                flightRepo.delete(flightId);
                return new ResponseEntity<Flight>(HttpStatus.OK);
            }
            return ResponseEntity.status(400).body("Flight not found");
        }
        catch (NumberFormatException e)
        {
            return ResponseEntity.status(400).body("Error parseing flight id");
        }
    }
}
