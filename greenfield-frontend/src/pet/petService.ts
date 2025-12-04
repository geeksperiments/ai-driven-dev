export interface Pet {
  id: number;
  name: string;
  ownerName: string;
}

export const petService = {
  getAllPets: async (): Promise<Pet[]> => {
    const response = await fetch('http://localhost:8080/pets');
    if (!response.ok) {
      throw new Error('Failed to fetch pets');
    }
    return response.json();
  },

  getPetByName: async (name: string): Promise<Pet> => {
    const response = await fetch(`http://localhost:8080/pets/name/${name}`);
    if (!response.ok) {
      throw new Error('Failed to fetch pet');
    }
    return response.json();
  },

  createPet: async (pet: Omit<Pet, 'id'>): Promise<Pet> => {
    const response = await fetch('http://localhost:8080/pets', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(pet),
    });
    if (!response.ok) {
      throw new Error('Failed to create pet');
    }
    return response.json();
  },
};