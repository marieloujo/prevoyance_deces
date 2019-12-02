<?php

namespace App\Repositories\Contrat\Interfaces;

interface ContratRepositoryInterface
{

    /**
     * Get's all contrats.
     *
     * @return mixed
     */
    public function all($user);

    /**
     * Deletes an contrat.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an contrat by it's ID
     *
     * @param int
     */
    public function getById($id);

    public function getByNumeroContrat($id);


    /**
     * Create a contrat.
     *
     * @param array
     */
    public function create(array $contrat_data);

    /**
     * Update an contrat.
     *
     * @param int
     * @param array
     */
    public function update($id, array $contrat_data);


}