<?php

namespace App\Repositories\User\Interfaces;

use App\User;

interface UserRepositoryInterface
{

    /**
     * Get's all users.
     *
     * @return mixed
     */
    public function all();

    /**
     * Logout user.
     *
     * @return mixed
     */
    public function logout();

    /**
     * Deletes an user.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an user by it's ID
     *
     * @param int
     */
    public function getById($id);

    /**
     * Get's an user by it's name
     *
     * @param string
     */
    public function getByName($name);

    public function getEvolution();


    /**
     * Get's an user by it's name
     *
     * @param string
     */
    public function getByPhone($phone);


    /**
     * Get's an user authenticate
     *
     * @param string
     */
    public function getAuth();

    /**
     * Login an user.
     *
     * @param array
     */
    public function login(array $user_data,$client_id,$client_secret);

    /**
     * Register an user.
     *
     * @param array
     */
    public function register(array $user_data,$model_id,$model_type,$actif,$prospect);

    /**
     * Update an user.
     *
     * @param int
     * @param array
     */
    public function update($id, array $user_data,$actif,$prospect,$model_id,$model_type);

}