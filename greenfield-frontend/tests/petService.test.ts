import { describe, it, expect, beforeEach, vi } from 'vitest';
import { petService } from '../src/pet/petService';

global.fetch = vi.fn();

describe('petService', () => {
  beforeEach(() => {
    vi.resetAllMocks();
  });

  it('should fetch all pets', async () => {
    const mockPets = [
      { id: 1, name: 'Buddy', ownerName: 'John' },
      { id: 2, name: 'Whiskers', ownerName: 'Jane' }
    ];

    (fetch as any).mockResolvedValueOnce({
      ok: true,
      json: async () => mockPets,
    });

    const result = await petService.getAllPets();
    expect(result).toEqual(mockPets);
    expect(fetch).toHaveBeenCalledWith('http://localhost:8080/pets');
  });

  it('should create a pet', async () => {
    const newPet = { name: 'Max', ownerName: 'Bob' };
    const createdPet = { id: 3, ...newPet };

    (fetch as any).mockResolvedValueOnce({
      ok: true,
      json: async () => createdPet,
    });

    const result = await petService.createPet(newPet);
    expect(result).toEqual(createdPet);
    expect(fetch).toHaveBeenCalledWith('http://localhost:8080/pets', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newPet),
    });
  });
});