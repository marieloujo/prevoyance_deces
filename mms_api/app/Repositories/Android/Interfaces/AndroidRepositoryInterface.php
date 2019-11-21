<?php

namespace App\Repositories\Entreprise\Interfaces;

interface EntrepriseRepositoryInterface
{

    /**
     * Get's a entreprise by it's ID
     *
     * @param int
     */
    public function getClient($id);
    /**
     * Get's all entreprises.
     *
     * @return mixed
     */
    public function allClient();

    
}