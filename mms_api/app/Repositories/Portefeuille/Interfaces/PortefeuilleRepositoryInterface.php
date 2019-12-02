<?php

namespace App\Repositories\Portefeuille\Interfaces;

interface PortefeuilleRepositoryInterface
{

    /**
     * Get's all comptes.
     *
     * @return mixed
     */
    public function all($date);

    /**
     * Deletes an compte.
     *
     * @param int
     */
    public function delete($date);

    /**
     * Get's an compte by it's ID
     *
     * @param int
     */
    public function getById($contrat,$id);

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