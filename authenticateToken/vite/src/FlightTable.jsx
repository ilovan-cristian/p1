import React from 'react'
import './FlightApp.css'

function FlightRow({ flight, onDelete, onUpdate }) {
    return (
        <tr>
            <td>{flight.id}</td>
            <td>{flight.location}</td>
            <td>{flight.destination}</td>
            <td>{new Date(flight.departure).toLocaleString()}</td>
            <td>{flight.seats}</td>
            <td>
                <button onClick={() => onDelete(flight.id)}>Delete</button>
                {' '}
                <button onClick={() => onUpdate(flight)}>Update</button>
            </td>
        </tr>
    )
}

export default function FlightTable({ flights, onDelete, onUpdate }) {
    return (
        <table className="center">
            <thead>
            <tr>
                <th>ID</th>
                <th>Location</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Seats</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {flights.map(f => (
                <FlightRow
                    key={f.id}
                    flight={f}
                    onDelete={onDelete}
                    onUpdate={onUpdate}
                />
            ))}
            </tbody>
        </table>
    )
}