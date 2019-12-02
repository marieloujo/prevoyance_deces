<?php

namespace App\Repositories\Marchand\Interfaces;

interface MarchandRepositoryInterface
{

    /**
     * Get's all marchands.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an marchand.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an marchand by it's ID
     *
     * @param int
     */
    public function getById($id);

    /**
     * Get's an marchand by it's name
     *
     * @param string
     */
    public function getByName($name);
    /**
     * Get's an marchand by it's name
     *
     * @param string
     */
    public function getByDepartement($name);

    /**
     * Create a marchand.
     *
     * @param array
     */
    public function create(array $marchand_data);

    /**
     * Update an marchand.
     *
     * @param int
     * @param array
     */
    public function update($id, array $marchand_data);


}