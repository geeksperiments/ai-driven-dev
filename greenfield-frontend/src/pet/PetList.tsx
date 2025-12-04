import { useState, useEffect } from 'react';
import { Pet, petService } from './petService';

export default function PetList() {
  const [pets, setPets] = useState<Pet[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPets = async () => {
      try {
        const data = await petService.getAllPets();
        setPets(data);
      } catch (err) {
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