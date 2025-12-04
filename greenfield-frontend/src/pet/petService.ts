export interface Pet {
  id: number;
  name: string;
  ownerName: string;
}

const API_BASE_URL = 'http://localhost:8080';

export const petService = {
  async getAllPets(): Promise<Pet[]> {
    const response = await fetch(`${API_BASE_URL}/pets`);
    if (!response.ok) {
      throw new Error('Failed to fetch pets');
    }
    return response.json();
  },

  async getPetByName(name: string): Promise<Pet> {
    const response = await fetch(`${API_BASE_URL}/pets/name/${name}`);
    if (!response.ok) {
      throw new Error('Failed to fetch pet');
    }
    return response.json();
  },

  async createPet(pet: Omit<Pet, 'id'>): Promise<Pet> {
    const response = await fetch(`${API_BASE_URL}/pets`, {
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