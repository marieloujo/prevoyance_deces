<?php

namespace App\Repositories\Compte\Interfaces;

interface CompteRepositoryInterface
{

    /**
     * Get's all comptes.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an compte.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an compte by it's ID
     *
     * @param int
     */
    public function getById($id);
    
    public function getComptes($marchand,$end);
    
    public function getCompte($marchand);

    /**
     * Create a compte.
     *
     * @param array
     */
    public function create(array $compte_data);

    /**
     * Update an compte.
     *
     * @param int
     * @param array
     */
    public function update($id, array $compte_data);


}