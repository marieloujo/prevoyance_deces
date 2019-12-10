<?php

namespace App\Repositories\Beneficiaire\Interfaces;

interface BeneficiaireRepositoryInterface
{

    /**
     * Get's all beneficiaires.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an beneficiaire.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an beneficiaire by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a beneficiaire.
     *
     * @param array
     */
    public function create();

    /**
     * Update an beneficiaire.
     *
     * @param int
     * @param array
     */
    public function update($id, array $beneficiaire_data);


}