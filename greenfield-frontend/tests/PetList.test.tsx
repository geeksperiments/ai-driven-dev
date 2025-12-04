import React from 'react';
import { describe, it, expect, vi } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import PetList from '../src/pet/PetList';
import { petService } from '../src/pet/petService';

vi.mock('../src/pet/petService');

describe('PetList', () => {
  it('should display pets when loaded', async () => {
    const mockPets = [
      { id: 1, name: 'Buddy', ownerName: 'John' },
      { id: 2, name: 'Whiskers', ownerName: 'Jane' }
    ];

    vi.mocked(petService.getAllPets).mockResolvedValue(mockPets);

    render(<PetList />);

    expect(screen.getByText('Loading pets...')).toBeTruthy();

    await waitFor(() => {
      expect(screen.getByText('Pet List')).toBeTruthy();
      expect(screen.getByText('Buddy')).toBeTruthy();
      expect(screen.getByText('Whiskers')).toBeTruthy();
    });
  });

  it('should display error message when fetch fails', async () => {
    vi.mocked(petService.getAllPets).mockRejectedValue(new Error('API Error'));

    render(<PetList />);

    await waitFor(() => {
      expect(screen.getByText('Error: Failed to load pets')).toBeTruthy();
    });
  });
});