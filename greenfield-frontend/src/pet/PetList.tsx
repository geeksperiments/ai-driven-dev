import { useState, useEffect } from 'react';

interface Pet {
  id: number;
  name: string;
  ownerName: string;
}

// Service methods defined inline to avoid import issues
const getAllPets = async (): Promise<Pet[]> => {
  const response = await fetch('http://localhost:8080/pets');
  if (!response.ok) {
    throw new Error('Failed to fetch pets');
  }
  return response.json();
};

export default function PetList() {
  const [pets, setPets] = useState<Pet[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPets = async () => {
      try {
        const data = await getAllPets();
        setPets(data);
      } catch (err) {
        console.error('Error fetching pets:', err);
        setError('Failed to load pets');
      } finally {
        setLoading(false);
      }
    };

    fetchPets();
  }, []);

  if (loading) return <div>Loading pets...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>Pet List</h2>
      {pets.length === 0 ? (
        <p>No pets found</p>
      ) : (
        <ul>
          {pets.map((pet) => (
            <li key={pet.id}>
              <strong>{pet.name}</strong> - Owner: {pet.ownerName}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}